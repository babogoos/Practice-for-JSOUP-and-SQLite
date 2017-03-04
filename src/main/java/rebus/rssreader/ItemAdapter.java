package rebus.rssreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by d-eye on 2017/3/3.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    // 畫面資源編號
    private int resource;
    // 包裝的記事資料
    private List<Item> items;
    public ItemAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        // 讀取目前位置的記事物件
        final Item item = getItem(position);

        if (convertView == null) {
            // 建立項目畫面元件
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)
                    getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        }
        else {
            itemView = (LinearLayout) convertView;
        }

        TextView dateTv = (TextView) itemView.findViewById(R.id.date_textView);
        TextView daynightTv = (TextView) itemView.findViewById(R.id.daynight_textView);
        TextView temperatureTv = (TextView) itemView.findViewById(R.id.temperature_textView);
        TextView weatherTv = (TextView) itemView.findViewById(R.id.weather_textView);
        dateTv.setText(item.getDate());
        daynightTv.setText(item.getDaynight());
        temperatureTv.setText(item.getTemperature());
        weatherTv.setText(item.getWeather());

        return itemView;
    }
    // 設定指定編號的記事資料
    public void set(int index, Item item) {
        if (index >= 0 && index < items.size()) {
            items.set(index, item);
            notifyDataSetChanged();
        }
    }

    // 讀取指定編號的記事資料
    public Item get(int index) {
        return items.get(index);
    }

    public void updateItemList(List<Item> new_items) {
        items.clear();
        items.addAll(new_items);
        this.notifyDataSetChanged();
    }
    public void ClearItemList() {
        items.clear();
        this.notifyDataSetChanged();
    }
}
