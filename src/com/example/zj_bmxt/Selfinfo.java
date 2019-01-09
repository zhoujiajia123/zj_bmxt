package com.example.zj_bmxt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Selfinfo extends Fragment {
	protected boolean isVisible;
	ImageView iv;
	TextView tView;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.activity_selfinfo, container, false);
		iv = (ImageView) view.findViewById(R.id.touxiang);
		tView = (TextView) view.findViewById(R.id.tname);
		String[] content = new String[] { "µÇÂ¼/×¢²á", "Èí¼þÐÅÏ¢", "ÏêÏ¸¸öÈËÐÅÏ¢", "×¢Ïú", "ÐÞ¸ÄÃÜÂë" ,"ÎÒµÄÕÐÆ¸","ÎÒµÄ×â·¿"};
		int[] img = new int[] { R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4 ,R.drawable.mima,R.drawable.zhaopin,R.drawable.zufang};
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int i=0;i<7;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("h", img[i]);
			map.put("c", content[i]);
			list.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.sl_detail,
				new String[] { "h", "c" }, new int[] { R.id.lhead1, R.id.lstr1 });
		ListView lView = (ListView) view.findViewById(R.id.slist);
		lView.setAdapter(simpleAdapter);

		lView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				// µÇÂ¼/×¢²á
				case 0:
					if (Person.login == 0) {
						Intent intent = new Intent();
						intent.setClass(getActivity(), LoginActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(getActivity(), "ÒÑµÇÂ¼", Toast.LENGTH_SHORT).show();
					}

					break;

				// ÉèÖÃ
				 case 1: 
					Toast.makeText(getActivity(), "°æÈ¨ËùÓÐ:ÖÜ´óÏÀ¹«Ë¾", Toast.LENGTH_SHORT).show();	
					break;
				// ÏêÏ¸ÐÅÏ¢
				case 2:
					if (Person.login == 1) {
						Intent intent = new Intent();
						intent.setClass(getActivity(), Detailinfo.class);
						startActivity(intent);
					} else {
						Toast.makeText(getActivity(), "ÉÐÎ´µÇÂ¼", Toast.LENGTH_SHORT).show();
					}
					break;

				// ×¢ÏúµÇÂ¼
				case 3:
					if (Person.login == 1) {
						choice();
					} else {
						Toast.makeText(getActivity(), "ÉÐÎ´µÇÂ¼", Toast.LENGTH_SHORT).show();
					}
					break;
				case 4:
					if (Person.login == 1) {
						Intent intent = new Intent();
						intent.setClass(getActivity(), Changepassword.class);
						startActivity(intent);
					} else {
						Toast.makeText(getActivity(), "ÉÐÎ´µÇÂ¼", Toast.LENGTH_SHORT).show();
					}
					break;
				case 5:
					if (Person.login == 1) {
						Intent intent=new Intent();
						intent.setClass(getActivity(), Myzhaopin.class);
						startActivity(intent);
					} else {
						Toast.makeText(getActivity(), "ÉÐÎ´µÇÂ¼", Toast.LENGTH_SHORT).show();
					}
					break;
				case 6:
					if (Person.login == 1) {
						Intent intent=new Intent();
						intent.setClass(getActivity(), Myzufang.class);
						startActivity(intent);
					} else {
						Toast.makeText(getActivity(), "ÉÐÎ´µÇÂ¼", Toast.LENGTH_SHORT).show();
					}
					break;

				default:
					break;
				}
			}
		});
		return view;
	}

	public void choice() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("×¢ÏúµÇÂ¼");
		pButton(builder);
		nButton(builder).create().show();
	}

	private AlertDialog.Builder pButton(AlertDialog.Builder builder) {
		return builder.setPositiveButton("È·¶¨", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Person.login = 0;
				Person.id=null;
				getActivity();
				preferences=getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
				editor=preferences.edit();
				editor.putString("id", Person.id);
				editor.putInt("login", Person.login);
				editor.commit();
				tView.setText("Î´µÇÂ¼");
				iv.setImageResource(R.drawable.lu);

			}
		});
	}

	private AlertDialog.Builder nButton(AlertDialog.Builder builder) {
		return builder.setNegativeButton("È¡Ïû", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
	}

	public void onResume() {
		super.onResume();
		if (Person.login == 1) {
			tView.setText("ÒÑµÇÂ¼");
			iv.setImageResource(R.drawable.nansheng);
		} else {
			tView.setText("Î´µÇÂ¼");
			iv.setImageResource(R.drawable.lu);
		}

	}

}