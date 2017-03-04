package rebus.rssreader;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Main extends Activity {

	private ArrayList<String> title;
	private ArrayList<String> description;
	private ArrayList<String> url;
	private ArrayList<String> data;
	private ProgressDialog loading;

	private String feed_rss = "http://www.cwb.gov.tw/rss/forecast/36_08.xml";
	private ListView list;
    private ItemDAO itemDAO;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		list = (ListView) findViewById(R.id.listView1);
		ParsingRSS parsingFeed = new ParsingRSS();
		parsingFeed.execute("");
        itemDAO = new ItemDAO(getApplicationContext());
	}

	private class ParsingRSS extends AsyncTask<String,String,String> {
		@Override
		protected void onPreExecute()
		{
			loading = new ProgressDialog(Main.this);
			loading.setMessage(getResources().getText(R.string.loading));
			loading.setCancelable(false);
			loading.show();
			title = new ArrayList<String>();
			description = new ArrayList<String>();
			url = new ArrayList<String>();
			data = new ArrayList<String>();
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				Document doc = Jsoup.connect(feed_rss)
						.userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
						.timeout((int) (9999 * 9999)).get();

				Elements elemento2 = doc.getElementsByTag("item");

                Element elemento3 = elemento2.get(1);
					String title_parsed = elemento3.getElementsByTag("title").first().text();
					String url_parsed = elemento3.getElementsByTag("guid").first().text();
					String data_parsed = elemento3.getElementsByTag("pubDate").first().text();
					String description_parsed = elemento3.getElementsByTag("description").first().text();

					Log.d("title: ", title_parsed);
					Log.d("url: ", url_parsed);
					Log.d("data: ", data_parsed);
					Log.d("description: ", description_parsed);
					title.add(title_parsed);
					url.add(url_parsed);
					data.add(data_parsed);
					description.add(description_parsed);

			} catch (Exception e) {
				Log.e("error", "parsing");
				
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			loading.dismiss();
			if (title.size() == 0) {
				Toast toast = Toast.makeText(Main.this,
    					getResources().getText(R.string.error),
    			    	Toast.LENGTH_LONG);
    			    	toast.show();
				finish();
			}
            String[] list_item = description.get(0).split("<BR>");
            for (int i = 0; i < list_item.length; i++) {
                String[] itemString = list_item[i].split(" ");
                Item item;
                if(i==0)
                    item = new Item(i,itemString[0],itemString[1],itemString[2]+itemString[3]+itemString[4],itemString[5]);
                else
                    item = new Item(i,itemString[1],itemString[2],itemString[3]+itemString[4]+itemString[5],itemString[6]);
                itemDAO.insert(item);
            }
            final ItemAdapter adapter = new ItemAdapter(Main.this, R.layout.singleitem, itemDAO.getAll());
            list.setAdapter(adapter);
			list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						final int pos, long id) {
                    itemDAO.delete(adapter.get(pos).getId());
                    adapter.updateItemList(itemDAO.getAll());
                    Toast toast = Toast.makeText(Main.this,
                            "Deleted",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    return true;
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.reload:
			ParsingRSS parsingFeed = new ParsingRSS();
			parsingFeed.execute("");
			return true;
		}
		return true;

	}

}
