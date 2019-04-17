package apiratehat.androidsamplecode.exp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import apiratehat.androidsamplecode.R;

public class IntroduceActivity extends AppCompatActivity implements View.OnClickListener {
    private String mHistory = "广东工业大学由原广东工学院、广东机械学院和华南建设学院（东院）于1995年6月合并组建而成。" +
            "学校已有60年的办学历史，是一所以工为主、工理经管文法艺结合、多科性协调发展的省属重点大学，" +
            "是广东省高水平大学重点建设高校。" +
            "学校坐落在中国南方名城广州，地理位置优越，校园占地总面积3066.67亩，拥有大学城、" +
            "东风路、龙洞等多个校区。大学城校区突出工科特色，多个学科相互促进协调发展，创设多个协同创新平台。" +
            "东风路校区突出艺术创意和社工服务氛围的营造，建有设计创意园和成果展示馆。" +
            "龙洞校区突出管理学与理学氛围的营造，打造环境优美、恬静怡人的花园式校园。";

    private String mTeacherInfo = "学校高度重视师资队伍建设，先后推出“百人计划”“青年百人计划”“培英育才计划”“教师出国研修计划”等，" +
            "师资力量不断增强。学校有职称自主评审权，现有专任教师2000多人，其中正高级职称300多人，" +
            "副高级职称约700人。目前，学校拥有全职院士、“长江学者”、国家“杰青”、国家“优青”、" +
            "教育部“新世纪优秀人才”等国家级人才80人次，拥有珠江学者、青年珠江学者、省杰青等省级人才99人次" +
            "，同时还聘有外籍院士4人、中国工程院院士4人，已组建并入选广东省“创新团队”9个。高素质师资队伍的建设，" +
            "为学校人才培养提供了强有力的支撑。目前全日制在校生44000余人，其中本科生36000余人，研究生7000余人，" +
            "并招有不同层次的成人学历教育学生、港澳台生和外国留学生，已形成“学士-硕士-博士”完整的人才培养体系。";

    private String mStudent = "学生在全国各类科技创新竞赛、文化体育竞赛中不断刷新纪录。" +
            "2013-2017年，学校连续三届在“挑战杯”全国大学生课外学术科技作品竞赛中捧得“优胜杯”，" +
            "其中2015年和2017年，我校学生团队均摘得2项特等奖；" +
            "在2012-2016年，学校连续三届在“创青春”全国大学生创业大赛（前身为“挑战杯”中国大学生创业计划竞赛）中夺得金奖；" +
            "2017年，我校FSAE车队荣获中国大学生方程式汽车大赛总成绩第一名；" +
            "2018年，我校学生荣获美国大学生数学建模竞赛一等奖18项；" +
            "2017-2018年，我校学生荣获德国RED DOT（红点）设计大奖1项，德国IF DEGIGN TALENT AWAARD 大奖4项；" +
            "近年来，学生在声乐、器乐和舞蹈集体项目上获得全国大学生艺术展演一等奖、全国大学生素质教育艺术品牌金奖等50余项。";
    private Button mBtnHistory;
    private Button mBtnTeacher;
    private Button mBtnStudent;

    private TextView mTvTitle;
    private TextView mTvContent;
    private ImageView mImvImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        mBtnHistory = findViewById(R.id.btn_history);
        mBtnTeacher = findViewById(R.id.btn_teacher);
        mBtnStudent = findViewById(R.id.btn_student);
        mTvTitle = findViewById(R.id.tv_title);
        mTvContent = findViewById(R.id.tv_content);
        mImvImage = findViewById(R.id.imv_image);

        mBtnHistory.setOnClickListener(this);
        mBtnTeacher.setOnClickListener(this);
        mBtnStudent.setOnClickListener(this);


        mImvImage.setImageResource(R.drawable.imv_history);
        mTvTitle.setText(mBtnHistory.getText());
        mTvContent.setText(mHistory);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_history:
                mImvImage.setImageResource(R.drawable.imv_history);
                mTvTitle.setText(mBtnHistory.getText());
                mTvContent.setText(mHistory);
                break;
            case R.id.btn_teacher:
                mImvImage.setImageResource(R.drawable.imv_teacher);
                mTvTitle.setText(mBtnTeacher.getText());
                mTvContent.setText(mTeacherInfo);
                break;
            case R.id.btn_student:
                mImvImage.setImageResource(R.drawable.imv_student);
                mTvTitle.setText(mBtnStudent.getText());
                mTvContent.setText(mStudent);
                break;
        }
    }
}
