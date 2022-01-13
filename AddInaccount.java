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
	EditText txtInMoney, txtInTime, txtInHandler, txtInMark;// ����4��EditText����
	Spinner spInType;
	Button btnInSaveButton;// ����Button���󡰱��桱
	Button btnInCancelButton;

	private int mYear;// ��
	private int mMonth;// ��
	private int mDay;// ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addinaccount);// ���ò����ļ�
		txtInMoney = (EditText) findViewById(R.id.txtInMoney);
		txtInTime = (EditText) findViewById(R.id.txtInTime);
		txtInHandler = (EditText) findViewById(R.id.txtInHandler);
		txtInMark = (EditText) findViewById(R.id.txtInMark);
		spInType = (Spinner) findViewById(R.id.spInType);// ��ȡ��������б�
		btnInSaveButton = (Button) findViewById(R.id.btnInSave);
		btnInCancelButton = (Button) findViewById(R.id.btnInCancel);

		txtInTime.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						showDialog(DATE_DIALOG_ID);// ��ʾ����ѡ��Ի���
					}
				});

		btnInSaveButton.setOnClickListener(new OnClickListener() {// Ϊ���水ť���ü����¼�
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String strInMoney = txtInMoney.getText().toString();// ��ȡ����ı����ֵ
						if (!strInMoney.isEmpty()) {
							
							InaccountDAO inaccountDAO = new InaccountDAO(
									AddInaccount.this);
							// ����Tb_inaccount����
							Tb_inaccount tb_inaccount = new Tb_inaccount(
									inaccountDAO.getMaxId() + 1, Double
											.parseDouble(strInMoney), txtInTime
											.getText().toString(), spInType
											.getSelectedItem().toString(),
									txtInHandler.getText().toString(),
									txtInMark.getText().toString());
							inaccountDAO.add(tb_inaccount);// ���������Ϣ
							// ������Ϣ��ʾ
							Toast.makeText(AddInaccount.this, "���������롽������ӳɹ���",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(AddInaccount.this, "�����������",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		btnInCancelButton.setOnClickListener(new OnClickListener() {// Ϊȡ����ť���ü����¼�
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						txtInMoney.setText("");// ���ý���ı���Ϊ��
						txtInMoney.setHint("0.00");// Ϊ����ı���������ʾ
						txtInTime.setText("");
						txtInTime.setHint("2011-01-01");
						txtInHandler.setText("");// ���ø���ı���Ϊ��
						txtInMark.setText("");
						spInType.setSelection(0);
					}
				});

		final Calendar c = Calendar.getInstance();// ��ȡ��ǰϵͳ����
		mYear = c.get(Calendar.YEAR);// ��ȡ���
		mMonth = c.get(Calendar.MONTH);// ��ȡ�·�
		mDay = c.get(Calendar.DAY_OF_MONTH);// ��ȡ����

		updateDisplay();// ��ʾ��ǰϵͳʱ��
	}

	@Override
	protected Dialog onCreateDialog(int id)// ��дonCreateDialog����
	{
		switch (id) {
		case DATE_DIALOG_ID:// ��������ѡ��Ի���
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;// Ϊ�·ݸ�ֵ
			mDay = dayOfMonth;
			updateDisplay();// ��ʾ���õ�����
		}
	};

	private void updateDisplay() {
		// ��ʾ���õ�ʱ��
		txtInTime.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));
	}
}
