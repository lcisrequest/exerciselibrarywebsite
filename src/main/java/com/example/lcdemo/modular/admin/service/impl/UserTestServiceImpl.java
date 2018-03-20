package com.example.lcdemo.modular.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.modular.admin.dao.*;
import com.example.lcdemo.modular.admin.dto.UserTestDTO;
import com.example.lcdemo.modular.admin.model.*;
import com.example.lcdemo.modular.admin.service.SubjectService;
import com.example.lcdemo.modular.admin.service.UserTestService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class UserTestServiceImpl implements UserTestService {
    @Autowired
    UserTestMapper userTestMapper;
    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    SubjectService subjectService;
    @Autowired
    TestMapper testMapper;
    @Autowired
    ErrorSubjectMapper errorSubjectMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 获取一套顺序测试
     */
    @Override
    public List<Map<String, Object>> getAOrderTest(int userId, int testNum, String testType) {
        if (testNum == 0 || testType == null || "".equals(testType)) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        Wrapper<Subject> wrapper = new EntityWrapper<>();
        if (!testType.equals("all")) {
            wrapper.eq("problem_type", testType);
        }
        List<Subject> list = subjectMapper.selectList(wrapper);//获取该类型的所有题目
        List<Integer> orderSubIds = this.getAllMyOrderTest(userId);
        List<Map<String, Object>> returnList = new ArrayList<>();
        int num = 0;
        if (list.size() == 0) {
            throw new LcException(LcExceptionEnum.SUBJECCT_IS_NULL);
        }
        for (Subject sub : list) {    //遍历所有习题
            if (num < testNum) {
                int subId = sub.getId(); //得到当前习题的id
                boolean isDone = false;
                for (int id : orderSubIds) {
                    if (id == subId) {        //判断该习题我是否做过了
                        isDone = true;
                    }
                }
                if (!isDone) {                //若该习题没做过
                    returnList.add(sub.getMap());    //则加入返回
                    num++;                  //习题数量+1
                }
            }
        }
        while (num < testNum) {  //若返回习题数量不够
            int notEnoughNum = testNum - num;
            Subject subject = this.getARandomSubject(testType); //随机获得一道该类型的题目
            returnList.add(subject.getMap());
            num++;
        }
        return returnList;
    }


    /**
     * 生成一套指定类型的随机练习
     *
     * @param testNum
     * @param testType
     * @return
     */
    @Override
    public List<Map<String, Object>> getARandomTest(int testNum, String testType) {
        if (testNum == 0 || testType == null || "".equals(testType)) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        List<Map<String, Object>> list = new ArrayList<>();
        int num = 0;
        while (num < testNum) {
            num++;
            Subject subject = this.getARandomSubject(testType);
            list.add(subject.getMap());
        }
        return list;
    }

    /**
     * 获取指定类型的一个随机题目
     *
     * @param testType
     * @return
     */
    @Override
    public Subject getARandomSubject(String testType) {
        Wrapper<Subject> wrapper = new EntityWrapper<>();
        if (!testType.equals("all")) {
            wrapper.eq("problem_type", testType);
        }
        List<Subject> list = subjectMapper.selectList(wrapper); //得到该类型的所有题目
        if (list.size() == 0) {
            return new Subject();
        }
        int listnum = list.size();      //得到该类型的的题目的数量
        Random rand = new Random();
        int randNum = rand.nextInt(listnum); //生成一个0-题目数量的随机数
        return list.get(randNum);
    }

    /**
     * 获取我做过的所有顺序练习的习题的id
     *
     * @param userId
     * @return
     */
    @Override
    public List<Integer> getAllMyOrderTest(int userId) {
        Wrapper<UserTest> wrapper = new EntityWrapper<>();
        wrapper.eq("test_type", "order"); //规定练习类型为顺序练习
        wrapper.eq("user_id", userId);
        List<UserTest> list = userTestMapper.selectList(wrapper);
        List<Integer> listInt = new ArrayList<>();
        if (list.size() == 0) {
            return listInt;
        }
        for (UserTest ut : list) {
            String subjectStr = ut.getSubjectId();
            String subjectIds[] = subjectStr.split(",");
            for (String subjectId : subjectIds) {
                listInt.add(Integer.valueOf(subjectId));
            }
        }
        return listInt;
    }


    /**
     * 提交练习（顺序练习和随机练习）
     * <p>
     * 。
     *
     * @param userTestDTO
     * @param userId
     */
    @Override
    public JSONObject submitTest(UserTestDTO userTestDTO, int userId) {
        UserTest userTest = new UserTest();
        String subjectIds = userTestDTO.getSubjectIds();
        String ids[] = subjectIds.split(","); //获取题目数组
        int idNum = ids.length;
        String answers = userTestDTO.getAnswers();
        String answersNum[] = answers.split(",");//获取答案数组
        int aNum = answersNum.length;
        if (aNum != idNum) {
            throw new LcException(LcExceptionEnum.ANSWER_NUM_IS_WRONG);//若答案数与习题数不同，则抛出异常
        }
        List<Boolean> listbool = new ArrayList<>();
        int isRightNum = 0;
        for (int x = 0; x < idNum; x++) {
            int subjectid = Integer.valueOf(ids[x]); //获取习题id
            int answer = Integer.valueOf(answersNum[x]);//获取答案
            boolean isRight = subjectService.subjectIsRight(subjectid, answer);//判断答案是否正确
            listbool.add(isRight);
            if (!isRight) {          //若不正确
                this.addToErrorSubject(subjectid, userId); //则添加到错题
            } else {
                isRightNum++;
            }
        }
        double rightRate = (double) isRightNum / (double) aNum; //计算出正确率
        double score = (double) 100 * rightRate;             //计算出最终得分
        DecimalFormat df = new DecimalFormat("#.0");
        score = Double.valueOf(df.format(score));
        userTest.setUserId(userId);
        userTest.setProblemType(userTestDTO.getProblemType());
        userTest.setTestType(userTestDTO.getTestType());
        userTest.setSubjectId(userTestDTO.getSubjectIds());
        userTest.setTestResult(userTestDTO.getAnswers());
        userTest.setSubjectNum(idNum);
        userTest.setScore(score + "");
        userTest.setStartTime(DateUtil.getTime());
        userTestMapper.insert(userTest);            //新增练习记录
        int newTestId = userTest.getId();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", listbool);
        jsonObject.put("userTestId", newTestId);
        jsonObject.put("score", score);
        return jsonObject;
    }

    /**
     * 提交练习(模拟练习)
     *
     * @param testId
     * @param answers
     * @param userId
     * @return
     */
    @Override
    public JSONObject submitMock(int testId, String answers, int userId) {
        if (testId == 0 || answers == null || "".equals(answers)) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        Test test = testMapper.selectById(testId);
        String subjectIds = test.getTestSubject();
        String ids[] = subjectIds.split(","); //获取题目数组
        int idNum = ids.length;
        String answersNum[] = answers.split(",");//获取答案数组
        int aNum = answersNum.length;
        if (aNum != idNum) {
            throw new LcException(LcExceptionEnum.ANSWER_NUM_IS_WRONG);//若答案数与习题数不同，则抛出异常
        }

        List<Boolean> listbool = new ArrayList<>();
        int rightNum = 0;
        for (int x = 0; x < idNum; x++) {
            int subjectid = Integer.valueOf(ids[x]); //获取习题id
            int answer = Integer.valueOf(answersNum[x]);//获取答案
            boolean isRight = subjectService.subjectIsRight(subjectid, answer);
            listbool.add(isRight);//判断答案是否正确
            if (!isRight) {         //若不正确
                this.addToErrorSubject(subjectid, userId);//则添加到错题
            } else {
                rightNum++;
            }
        }
        double rightRate = (double) rightNum / (double) aNum;           //计算出正确率
        double Score = Double.valueOf(test.getTestFraction()) * rightRate; //计算出最终得分
        DecimalFormat df = new DecimalFormat("#.0");
        Score = Double.valueOf(df.format(Score));                       //精确到小数点后一位
        StringBuilder returnStr = new StringBuilder();
        returnStr.append("您在模拟练习中答对了").append(rightNum).append("道题,您的准确率为").append(rightRate).append(",该模拟测试的总分为")
                .append(test.getTestFraction()).append("分,您的成绩为").append(Score).append("分");
        if (rightRate == 1) {
            returnStr.append(",恭喜您获得了满分，请再接再厉!");
        } else {
            returnStr.append(",请继续努力！");
        }
        UserTest userTest = new UserTest();
        userTest.setUserId(userId);
        userTest.setProblemType(test.getProblemType());
        userTest.setTestType("mock");
        userTest.setSubjectId(subjectIds);
        userTest.setTestResult(answers);
        userTest.setSubjectNum(idNum);
        userTest.setScore(Score + "");
        userTest.setStartTime(DateUtil.getTime());
        userTest.setTestId(testId);
        userTestMapper.insert(userTest);            //新增练习记录
        int newTestId = userTest.getId();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", listbool);
        jsonObject.put("str", returnStr);
        jsonObject.put("userTestId", newTestId);
        jsonObject.put("score", Score);
        return jsonObject;
    }

    /**
     * 添加到错题
     *
     * @param subjectId
     * @param userId
     */
    @Override
    public void addToErrorSubject(int subjectId, int userId) {
        ErrorSubject errorSubject = new ErrorSubject();
        errorSubject.setSubjectId(subjectId);
        errorSubject.setUserId(userId);
        ErrorSubject err = errorSubjectMapper.selectOne(errorSubject); //判断是否添加过该错题
        if (err == null) {
            errorSubjectMapper.insert(errorSubject);//若没添加过，则添加该错题
        }
    }


    /**
     * 分页获取我的指定类型的错题
     *
     * @param courseType
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllMyErrorSubject(String courseType, int userId, int page, int limit) {
        if (courseType == null || "".equals(courseType) || page == 0 || limit == 0) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        if (courseType.equals("all")) {
            courseType = "%";
        }
        List<ErrorSubject> listErr = errorSubjectMapper.getMyError(courseType, userId, (page - 1) * limit, limit);
        List<Map<String, Object>> list = new ArrayList<>();
        for (ErrorSubject es : listErr) {
            int subjectId = es.getSubjectId();
            Subject subject = subjectMapper.selectById(subjectId);
            Map<String, Object> map = subject.getMap();
            list.add(map);
        }
        return list;
    }


    /**
     * 获取我的指定类型的错题数量
     *
     * @param courseType
     * @param userId
     * @return
     */
    @Override
    public Integer getAllMyErrorSubjectNum(String courseType, int userId) {
        if (courseType.equals("all")) {
            courseType = "%";
        }
        Integer num = errorSubjectMapper.getMyErrorNum(courseType, userId);
        return num;
    }

    /**
     * 删除错题
     *
     * @param subjectId
     * @param userId
     */
    @Override
    public void deleteError(int subjectId, int userId) {
        ErrorSubject errorSubject = new ErrorSubject();
        errorSubject.setUserId(userId);
        errorSubject.setSubjectId(subjectId);
        ErrorSubject err = errorSubjectMapper.selectOne(errorSubject);
        if (err != null) {//判断是否有该错题
            errorSubjectMapper.deleteById(err.getId());//若有则删除
        }
    }


    /**
     * 提交练习(错题练习)
     *
     * @param userTestDTO
     * @param userId
     * @return
     */
    @Override
    public JSONObject submitErrorSubject(UserTestDTO userTestDTO, int userId) {
        UserTest userTest = new UserTest();
        String subjectIds = userTestDTO.getSubjectIds();
        String ids[] = subjectIds.split(","); //获取题目数组
        int idNum = ids.length;
        String answers = userTestDTO.getAnswers();
        String answersNum[] = answers.split(",");//获取答案数组
        int aNum = answersNum.length;
        if (aNum != idNum) {
            throw new LcException(LcExceptionEnum.ANSWER_NUM_IS_WRONG);//若答案数与习题数不同，则抛出异常
        }
        int rightNum = 0;
        List<Boolean> listbool = new ArrayList<>();
        for (int x = 0; x < idNum; x++) {
            int subjectid = Integer.valueOf(ids[x]); //获取习题id
            int answer = Integer.valueOf(answersNum[x]);//获取答案
            boolean isRight = subjectService.subjectIsRight(subjectid, answer);//判断答案是否正确
            listbool.add(isRight);
            if (!isRight) {          //若不正确
                this.addToErrorSubject(subjectid, userId); //则添加到错题
            } else {                 //若正确
                this.deleteError(subjectid, userId);       //则从错题删除
                rightNum++;
            }
        }
        double rightRate = (double) rightNum / (double) aNum;           //计算出正确率
        double score = (double) 100 * rightRate;                        //计算出最终得分
        DecimalFormat df = new DecimalFormat("#.0");            //精确到小数点后一位
        score = Double.valueOf(df.format(score));
        userTest.setUserId(userId);
        userTest.setProblemType(userTestDTO.getProblemType());
        userTest.setTestType("error");
        userTest.setSubjectId(userTestDTO.getSubjectIds());
        userTest.setTestResult(userTestDTO.getAnswers());
        userTest.setSubjectNum(idNum);
        userTest.setScore(score + "");
        userTest.setStartTime(DateUtil.getTime());
        userTestMapper.insert(userTest);            //新增练习记录
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", listbool);
        jsonObject.put("userTestId", userTest.getId());
        jsonObject.put("score", score);
        return jsonObject;
    }


    /**
     * 分页获取指定类型的模拟练习
     *
     * @param problemType
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllUserTest(String problemType, int page, int limit) {
        Wrapper<Test> wrapper = new EntityWrapper<>();
        if (!problemType.equals("all")) {                      //当类型为all时，为不指定类型
            wrapper.eq("problem_type", problemType);                       //指定题目类型
        }
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);//分页
        List<Test> list = testMapper.selectPage(rowBounds, wrapper);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (Test t : list) {
            Map<String, Object> map = t.getMap();
            String sId = t.getTestSubject();
            String[] subjectIds = sId.split(",");//获取到该练习的所有题目id
            List<Map<String, Object>> listSubject = new ArrayList<>();
            for (String id : subjectIds) {
                Map<String, Object> subjectMap = this.getSubjectById(Integer.valueOf(id));//根据题目id取得题目信息
                listSubject.add(subjectMap);
            }
            map.put("subjectList", listSubject);
            listMap.add(map);
        }
        return listMap;
    }


    /**
     * 获取指定类型的模拟练习数量
     *
     * @param problemType
     * @return
     */
    @Override
    public int getTestNum(String problemType) {
        Wrapper<Test> wrapper = new EntityWrapper<>();
        if (!problemType.equals("all")) {                      //当类型为all时，为不指定类型
            wrapper.eq("problem_type", problemType);                       //指定题目类型
        }
        int count = testMapper.selectCount(wrapper);
        return count;
    }

    public Map<String, Object> getSubjectById(int subjectId) {
        Subject subject = subjectMapper.selectById(subjectId);
        if (subject == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        Map<String, Object> map = subject.getMap();
        return map;
    }

    /**
     * 分页获取我的指定类型的练习记录
     *
     * @param problemType
     * @param page
     * @param limit
     * @param userId
     * @return
     */
    @Override
    public List<UserTest> selectMyUserTest(String problemType, int page, int limit, int userId) {
        Wrapper<UserTest> wrapper = new EntityWrapper<>();
        if (!problemType.equals("all")) {
            wrapper.eq("problem_type", problemType);
        }
        wrapper.eq("user_id", userId);
        wrapper.orderBy("start_time", false);
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);//分页
        List<UserTest> listUT = userTestMapper.selectPage(rowBounds, wrapper);
        return listUT;
    }

    /**
     * 获取到我的指定类型的练习数量
     *
     * @param problemType
     * @param userId
     * @return
     */
    @Override
    public Integer selectMyUserTestCount(String problemType, int userId) {
        Wrapper<UserTest> wrapper = new EntityWrapper<>();
        if (!problemType.equals("all")) {
            wrapper.eq("problem_type", problemType);
        }
        wrapper.eq("user_id", userId);
        return userTestMapper.selectCount(wrapper);
    }

    /**
     * 获取指定模拟练习的排行榜
     *
     * @param testId
     * @return
     */
    @Override
    public List<Map<String, Object>> getRankForTest(int testId) {
        Wrapper<UserTest> wrapper = new EntityWrapper<>();
        wrapper.eq("test_id", testId);
        wrapper.orderBy("score", false);
        RowBounds rowBounds = new RowBounds(0, 10);//分页
        List<UserTest> list = userTestMapper.selectPage(rowBounds, wrapper);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (UserTest ut : list) {
            Map<String, Object> map = new HashMap<>();
            int userId = ut.getUserId();
            UserInfo user = userInfoMapper.selectById(userId);
            map.put("nickname", user.getNickname());
            map.put("userimg", user.getUserimg());
            map.put("startTime", ut.getStartTime());
            map.put("score", ut.getScore());
            listMap.add(map);
        }
        return listMap;
    }


    /**
     * 获取指定模拟练习的今日排行榜
     *
     * @param testId
     * @return
     */
    @Override
    public List<Map<String, Object>> getTodayRankForTest(int testId) {
        String time = DateUtil.getDay() + " 00:00:00";  //取到今日的时间
        List<UserTest> listMap = userTestMapper.selectUserTestRank(testId, time);
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserTest ut : listMap) {
            Map<String, Object> map = new HashMap<>();
            int userId = ut.getUserId();
            UserInfo user = userInfoMapper.selectById(userId);
            map.put("nickname", user.getNickname());
            map.put("userimg", user.getUserimg());
            map.put("startTime", ut.getStartTime());
            map.put("score", ut.getScore());
            list.add(map);
        }
        return list;
    }

    /**
     * 可根据练习类型和课程类型获取平均分，总分，总练习数，总题目数
     *
     * @param problemType
     * @param userId
     * @return
     */
    public JSONObject getMyTestNum(String problemType, String testType, int userId) {
        Wrapper<UserTest> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        if (!problemType.equals("all")) {             //当值为all时，表示获取所有
            wrapper.eq("problem_type", problemType);
        }
        if (!testType.equals(testType)) {             //当值为all时，表示获取所有
            wrapper.eq("test_type", testType);
        }
        List<UserTest> list = userTestMapper.selectList(wrapper);
        int testCount = list.size();                //练习总数
        double allscore = 0;                        //总分数
        int subjectCount = 0;                       //题目总数
        int rightNum = 0;                           //正确的题目数量
        for (UserTest ut : list) {
            allscore = Double.valueOf(ut.getScore()) + allscore;
            subjectCount = ut.getSubjectNum() + subjectCount;
            rightNum = rightNum + this.getRightNumByTestId(ut.getId()); //累加正确的题目数量
        }
        double average = allscore / testCount;      //计算出平均分
        double rightRate = rightNum / subjectCount; //正确率 = 正确题目数 / 题目总数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("testCount", testCount);
        jsonObject.put("allscore", allscore);
        jsonObject.put("subjectCount", subjectCount);
        jsonObject.put("average", average);
        jsonObject.put("rightRate", rightRate);
        return jsonObject;
    }

    /**
     * 根据用户练习id得到正确的题目数量
     *
     * @param userTestId
     * @return
     */
    @Override
    public int getRightNumByTestId(int userTestId) {
        UserTest userTest = userTestMapper.selectById(userTestId);
        String subjectStr = userTest.getSubjectId();
        String answerStr = userTest.getTestResult();
        String[] subjects = subjectStr.split(",");
        String[] answers = answerStr.split(",");
        int flag = 0;
        int rightNum = 0;
        for (String subjectId : subjects) {
            Subject subject = subjectMapper.selectById(subjectId);
            int rightAnswer = subject.getRightKey();
            int answer = Integer.valueOf(answers[flag++]);
            if (answer == rightAnswer) {
                rightNum++;
            }
        }
        return rightNum;
    }

    /**
     * 根据id获取用户练习详情
     *
     * @param userTestId
     * @return
     */
    @Override
    public UserTest getUserTestById(int userTestId) {
        UserTest userTest = userTestMapper.selectById(userTestId);
        if (userTest == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        return userTest;
    }
}
