/* Copyright 2010 ESRI
 *
 * All rights reserved under the copyright laws of the United States
 * and applicable international laws, treaties, and conventions.
 *
 * You may freely redistribute and use this sample code, with or
 * without modification, provided you include the original copyright
 * notice and use restrictions.
 *
 * See the 揝ample code usage restrictions� document for further information.
 *
 */

package com.esri.arcgis.android.samples;

import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ZoomControls;

import com.esri.android.map.MapView;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;

public class Main_activity extends Activity {

	MapView map = null;
	
    /** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		map = (MapView) findViewById(R.id.map);
		
		//用于显示下拉框选项
		final String[] citys={"","北京","朝阳","顺义","怀柔","通州","昌平","延庆","丰台","石景山","大兴","房山","密云","门头沟",
				"平谷","八达岭","佛爷顶","汤河口","密云上甸子","斋堂","霞云岭","北京城区","海淀","天津","宝坻","东丽","西青","北辰",
				"蓟县","汉沽","静海","津南","塘沽","大港","武清","宁河","上海","宝山","嘉定","南汇","浦东","青浦","松江","奉贤",
				"崇明","徐家汇","闵行","金山","石家庄","张家口","承德","唐山","秦皇岛","沧州","衡水","邢台","邯郸","保定","廊坊",
				"郑州","新乡","许昌","平顶山","信阳","南阳","开封","洛阳","商丘","焦作","鹤壁","濮阳","周口","漯河","驻马店","三门峡",
				"济源","安阳","合肥","芜湖","淮南","马鞍山","安庆","宿州","阜阳","亳州","黄山","滁州","淮北","铜陵","宣城","六安","巢湖",
				"池州","蚌埠","杭州","舟山","湖州","嘉兴","金华","绍兴","台州","温州","丽水","衢州","宁波","重庆","合川","南川","江津",
				"万盛","渝北","北碚","巴南","长寿","黔江","万州天城","万州龙宝","涪陵","开县","城口","云阳","巫溪","奉节","巫山","潼南",
				"垫江","梁平","忠县","石柱","大足","荣昌","铜梁","璧山","丰都","武隆","彭水","綦江","酉阳","秀山","沙坪坝","永川","福州",
				"泉州","漳州","龙岩","晋江","南平","厦门","宁德","莆田","三明","兰州","平凉","庆阳","武威","金昌","嘉峪关","酒泉","天水",
				"武都","临夏","合作","白银","定西","张掖","广州","惠州","梅州","汕头","深圳","珠海","佛山","肇庆","湛江","江门","河源",
				"清远","云浮","潮州","东莞","中山","阳江","揭阳","茂名","汕尾","韶关","南宁","柳州","来宾","桂林","梧州","防城港",
				"贵港","玉林","百色","钦州","河池","北海","崇左","贺州","贵阳","安顺","都匀","兴义","铜仁","毕节","六盘水","遵义",
				"凯里","昆明","红河","文山","玉溪","楚雄","普洱","昭通","临沧","怒江","香格里拉","丽江","德宏","景洪","大理","曲靖",
				"保山","呼和浩特","乌海","集宁","通辽","阿拉善左旗","鄂尔多斯","临河","锡林浩特","呼伦贝尔","乌兰浩特","包头","赤峰",
				"南昌","上饶","抚州","宜春","鹰潭","赣州","景德镇","萍乡","新余","九江","吉安","武汉","黄冈","荆州","宜昌","恩施",
				"十堰","神农架","随州","荆门","天门","仙桃","潜江","襄樊","鄂州","孝感","黄石","咸宁","成都","自贡","绵阳","南充",
				"达州","遂宁","广安","巴中","泸州","宜宾","内江","资阳","乐山","眉山","凉山","雅安","甘孜","阿坝","德阳","广元",
				"攀枝花","银川","中卫","固原","石嘴山","吴忠","西宁","黄南","海北","果洛","玉树","海西","海东","海南","济南",
				"潍坊","临沂","菏泽","滨州","东营","威海","枣庄","日照","莱芜","聊城","青岛","淄博","德州","烟台","济宁","泰安",
				"西安","延安","榆林","铜川","商洛","安康","汉中","宝鸡","咸阳","渭南","太原","临汾","运城","朔州","忻州","长治",
				"大同","阳泉","晋中","晋城","吕梁","乌鲁木齐","石河子","昌吉","吐鲁番","库尔勒","阿拉尔","阿克苏","喀什","伊宁",
				"塔城","哈密","和田","阿勒泰","阿图什","博乐","克拉玛依","拉萨","山南","阿里","昌都","那曲","日喀则","林芝","台北县",
				"高雄","台中","海口","三亚","东方","临高","澄迈","儋州","昌江","白沙","琼中","定安","屯昌","琼海","文昌","保亭",
				"万宁","陵水","西沙","南沙岛","乐东","五指山","琼山","长沙","株洲","衡阳","郴州","常德","益阳","娄底","邵阳",
				"岳阳","张家界","怀化","黔阳","永州","吉首","湘潭","南京","镇江","苏州","南通","扬州","宿迁","徐州","淮安","连云港",
				"常州","泰州","无锡","盐城","哈尔滨","丹江","佳木斯","绥化","黑河","双鸭山","伊春","大庆","七台河","鸡西","鹤岗",
				"齐齐哈尔","大兴安岭","长春","延吉","四平","白山","白城","辽源","松原","吉林","通化","沈阳","鞍山","抚顺","本溪",
				"丹东","葫芦岛","营口","阜新","辽阳","铁岭","朝阳","盘锦","大连","锦州"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main_activity.this,
				android.R.layout.simple_spinner_item,citys);
        //设置下拉框的属性
		final Spinner spin = (Spinner) findViewById(R.id.spinner1);
        spin.setPrompt("请选择城市");
        spin.setAdapter(adapter);
        //设置缩放按钮
        ZoomControls zoom = (ZoomControls) findViewById(R.id.zoomControls1);
        zoom.setIsZoomInEnabled(true);
        zoom.setIsZoomOutEnabled(true);
        
        //实现缩放功能
        zoom.setOnZoomInClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				map.zoomin();
			}
		});
        zoom.setOnZoomOutClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				map.zoomout();
			}
		});
        
        //实现地图点击功能
		map.setOnSingleTapListener(new OnSingleTapListener() {
            private  static final  long serialVersionUID = 1L;
			@Override
			public void onSingleTap(float x, float y) {
				// TODO Auto-generated method stub
				//获取点击位置坐标,并转换为4326坐标系统
				com.esri.core.geometry.Point pt = map.toMapPoint(x, y);
				com.esri.core.geometry.Point p=(Point) GeometryEngine.project(pt, map.getSpatialReference()	,
						SpatialReference.create(4326));
				try {
					String s=WebJson.GetPro(p);  //获取省份信息
					int i=WebJson.GetNum(s);     //获取对应ID
					URL url=new URL("http://www.weather.com.cn/adat/cityinfo/"+i+".html");//构建查询请求
					Weatherinfo weather=WebJson.GetWebJson(url);  //获得对应天气信息
					//生成提示框
					new  AlertDialog.Builder(Main_activity.this).setTitle(s)
					.setMessage("省会："+weather.City+"\n天气："+
					weather.Weather+"\n最低温："+weather.LowTemp+"\n最高温："+weather.UpperTemp)
					.setPositiveButton("确定", null).show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
        });
		//Retrieve the non-configuration instance data that was previously returned. 
		Object init = getLastNonConfigurationInstance();
		if (init != null) {
			map.restoreState((String) init);
		}
		
		//实现下拉框选择功能
		spin.setOnItemSelectedListener(new OnItemSelectedListener() 
		{    
			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String str=adapter.getItemAtPosition(position).toString();//获得选项
				
				try {
					com.esri.core.geometry.Point point=WebJson.GetPoint(str);//获得对应坐标位置
					map.zoomTo((Point) GeometryEngine.project(point,SpatialReference.create(4326) ,
						map.getSpatialReference())	, 1);//缩放至对应坐标
					//弹出天气信息提示框
					int i=WebJson.GetNum2(str);
					URL url=new URL("http://www.weather.com.cn/adat/cityinfo/"+i+".html");
					Weatherinfo weather=WebJson.GetWebJson(url);
					new  AlertDialog.Builder(Main_activity.this).setTitle("天气信息")
					.setMessage("城市："+weather.City+"\n天气："+
					weather.Weather+"\n最低温："+weather.LowTemp+"\n最高温："+weather.UpperTemp)
					.setPositiveButton("确定", null).show();
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> adapter) {
				// TODO Auto-generated method stub
				
			}
		});


        

	}

	/** Called by the system, as part of destroying an activity due to a configuration change. */
	public Object onRetainNonConfigurationInstance() {
		return map.retainState();
	}

}