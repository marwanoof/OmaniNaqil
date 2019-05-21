package unus.com.omaninaqil;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * Author: jpeng
 * Date: 17-9-12 下午6:50
 * E-mail:peng8350@gmail.com
 * Description:
 */
public class MyAdapter extends BaseAdapter
{
    private Context context;
    private ListBean[] mDatas;

    public MyAdapter(Context context, ListBean[] mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_menu,null);
            holder.tv = (TextView) convertView.findViewById(R.id.tv_text);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(mDatas[position].getTitle());
        holder.iv.setImageResource(mDatas[position].getResource());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pageName = mDatas[position].getTitle();
                if (pageName.equals("الصفحة الرئيسية")){
                    Intent goToPage = new Intent(context, MainMenuUser.class);
                    context.startActivity(goToPage);
                }else if (pageName.equals("طلب نقل حمولة")){
                    Intent goToPage = new Intent(context, TransportPackeg.class);
                    context.startActivity(goToPage);
                }else if (pageName.equals("تتبع الحمولة")){
                    Intent goToPage = new Intent(context, RequestMain.class);
                    context.startActivity(goToPage);
                }else if (pageName.equals("تسجيل الخروج")){
                    Intent goToPage = new Intent(context, LoginPage.class);
                    context.startActivity(goToPage);
                }else if (pageName.equals("تسجيل الدخول")){
                    Intent goToPage = new Intent(context, LoginPage.class);
                    context.startActivity(goToPage);
                }else if (pageName.equals("مستخدم جديد")){
                    Intent goToPage = new Intent(context, Registeration.class);
                    context.startActivity(goToPage);
                }else if (pageName.equals("حول التطبيق")){

                }
            }
        });
        return convertView;
    }

    static class ViewHolder{
        TextView tv;
        ImageView iv;
    }
}
