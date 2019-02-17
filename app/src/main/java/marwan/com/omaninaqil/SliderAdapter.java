package marwan.com.omaninaqil;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public int[] list_image = {
            R.drawable.slider1,
            R.drawable.slider2,
            R.drawable.slider3
    };
    public String[] list_title = {
            "نظام لوجستي متكامل",
            "سهولة الطلب",
            "تتبع الحمولة"
    };
    public String[] list_desc = {
            "تبيق الناقل العماني هو أول عماني لإدارة الشحن اللوجستي في السلطنة",
            "بإمكانك طلب نقل حمولة بكل سهولة وسرعة",
            "حرصا على الدقة والشفافية بإمكانك تتبع الحمولة التي قمت بإرسالها"
    };
    public int[] list_colors = {
            Color.rgb(0,174,239),
            Color.rgb(44,47,69),
            Color.rgb(243,104,35)
    };

    public SliderAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return list_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(RelativeLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.starterslider,container,false);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.starter_layout);
        ImageView imageView = view.findViewById(R.id.img_starter);
        TextView title = view.findViewById(R.id.title_starter);
        TextView desc = view.findViewById(R.id.desc_starter);

        relativeLayout.setBackgroundColor(list_colors[position]);
        imageView.setImageResource(list_image[position]);
        title.setText(list_title[position]);
        desc.setText(list_desc[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
