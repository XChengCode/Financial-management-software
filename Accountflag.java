package com.xiaoke.accountsoft.activity;

import com.xiaoke.accountsoft.dao.FlagDAO;
import com.xiaoke.accountsoft.model.Tb_flag;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Accountflag extends Activity {
	EditText txtFlag;
	Button btnflagSaveButton;
	Button btnflagCancelButton;// 创建Button组件对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountflag);

		txtFlag = (EditText) findViewById(R.id.txtFlag);
		btnflagSaveButton = (Button) findViewById(R.id.btnflagSave);
		btnflagCancelButton = (Button) findViewById(R.id.btnflagCancel);
		btnflagSaveButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String strFlag = txtFlag.getText().toString();
						if (!strFlag.isEmpty()) {
							FlagDAO flagDAO = new FlagDAO(Accountflag.this);// 创建FlagDAO对象
							Tb_flag tb_flag = new Tb_flag(
									flagDAO.getMaxId() + 1, strFlag);
							flagDAO.add(tb_flag);// 添加便签信息
							
							Toast.makeText(Accountflag.this, "〖新增便签〗数据添加成功！",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Accountflag.this, "请输入便签！",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		btnflagCancelButton.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						txtFlag.setText("");// 清空便签文本框
					}
				});
	}
}
