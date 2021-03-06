package com.xiaoke.accountsoft.activity;

import java.util.Calendar;

import com.xiaoke.accountsoft.dao.InaccountDAO;
import com.xiaoke.accountsoft.model.Tb_inaccount;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddInaccount extends Activity {
	protected static final int DATE_DIALOG_ID = 0;
	EditText txtInMoney, txtInTime, txtInHandler, txtInMark;// 创建4个EditText对象
	Spinner spInType;
	Button btnInSaveButton;// 创建Button对象“保存”
	Button btnInCancelButton;

	private int mYear;// 年
	private int mMonth;// 月
	private int mDay;// 日

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addinaccount);// 设置布局文件
		txtInMoney = (EditText) findViewById(R.id.txtInMoney);
		txtInTime = (EditText) findViewById(R.id.txtInTime);
		txtInHandler = (EditText) findViewById(R.id.txtInHandler);
		txtInMark = (EditText) findViewById(R.id.txtInMark);
		spInType = (Spinner) findViewById(R.id.spInType);// 获取类别下拉列表
		btnInSaveButton = (Button) findViewById(R.id.btnInSave);
		btnInCancelButton = (Button) findViewById(R.id.btnInCancel);

		txtInTime.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						showDialog(DATE_DIALOG_ID);// 显示日期选择对话框
					}
				});

		btnInSaveButton.setOnClickListener(new OnClickListener() {// 为保存按钮设置监听事件
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String strInMoney = txtInMoney.getText().toString();// 获取金额文本框的值
						if (!strInMoney.isEmpty()) {
							
							InaccountDAO inaccountDAO = new InaccountDAO(
									AddInaccount.this);
							// 创建Tb_inaccount对象
							Tb_inaccount tb_inaccount = new Tb_inaccount(
									inaccountDAO.getMaxId() + 1, Double
											.parseDouble(strInMoney), txtInTime
											.getText().toString(), spInType
											.getSelectedItem().toString(),
									txtInHandler.getText().toString(),
									txtInMark.getText().toString());
							inaccountDAO.add(tb_inaccount);// 添加收入信息
							// 弹出信息提示
							Toast.makeText(AddInaccount.this, "〖新增收入〗数据添加成功！",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(AddInaccount.this, "请输入收入金额！",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		btnInCancelButton.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						txtInMoney.setText("");// 设置金额文本框为空
						txtInMoney.setHint("0.00");// 为金额文本框设置提示
						txtInTime.setText("");
						txtInTime.setHint("2011-01-01");
						txtInHandler.setText("");// 设置付款方文本框为空
						txtInMark.setText("");
						spInType.setSelection(0);
					}
				});

		final Calendar c = Calendar.getInstance();// 获取当前系统日期
		mYear = c.get(Calendar.YEAR);// 获取年份
		mMonth = c.get(Calendar.MONTH);// 获取月份
		mDay = c.get(Calendar.DAY_OF_MONTH);// 获取天数

		updateDisplay();// 显示当前系统时间
	}

	@Override
	protected Dialog onCreateDialog(int id)// 重写onCreateDialog方法
	{
		switch (id) {
		case DATE_DIALOG_ID:// 弹出日期选择对话框
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;// 为月份赋值
			mDay = dayOfMonth;
			updateDisplay();// 显示设置的日期
		}
	};

	private void updateDisplay() {
		// 显示设置的时间
		txtInTime.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));
	}
}
