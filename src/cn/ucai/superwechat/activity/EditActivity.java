package cn.ucai.superwechat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.ucai.superwechat.R;

public class EditActivity extends BaseActivity{
	private EditText editText;

    private String oldGroupName;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_edit);
		
		editText = (EditText) findViewById(R.id.edittext);
		String title = getIntent().getStringExtra("title");
		String data = getIntent().getStringExtra("data");
		if(title != null)
			((TextView)findViewById(R.id.tv_title)).setText(title);
		if(data != null) {
            editText.setText(data);
            oldGroupName = data;
        }
		editText.setSelection(editText.length());
		
	}
	
	
	public void save(View view){
        String groupName = editText.getText().toString();
		if(groupName.isEmpty()||groupName.trim().isEmpty()){
            startActivity(new Intent(this, AlertDialog.class).putExtra("msg", "群组名称不能为空"));
        }else if(oldGroupName.equals(groupName.trim())){
            startActivity(new Intent(this, AlertDialog.class).putExtra("msg", "群组名称没有修改"));
        }else {
            setResult(RESULT_OK, new Intent().putExtra("data", editText.getText().toString()));
            finish();
        }
	}
}
