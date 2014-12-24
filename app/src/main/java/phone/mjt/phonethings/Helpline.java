package phone.mjt.phonethings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Helpline extends ActionBarActivity {

    public HelplineListAdaptor helplineListAdaptor;
    public ArrayList<PhoneNum> phoneArrayList = new ArrayList<PhoneNum>();
    private ListView listPhoneView;
    private Context context;

    public Helpline () {

        PhoneNum section = new PhoneNum("Police Control","100");
        phoneArrayList.add(section);

        section = new PhoneNum("my home","4281582");
        phoneArrayList.add(section);

        section = new PhoneNum("Police Emergency Number 1","4228435");
        phoneArrayList.add(section);

        section = new PhoneNum("Police Emergency Number 2","4226853");
        phoneArrayList.add(section);

        section = new PhoneNum("Fire Brigade","101");
        phoneArrayList.add(section);

        section = new PhoneNum("Paropakar Ambulance Service","4260859");
        phoneArrayList.add(section);

        section = new PhoneNum("Redcross Ambulance Service ","4228094");
        phoneArrayList.add(section);

        section = new PhoneNum("Lalitpur Redcross Ambulance Service","5545666");
        phoneArrayList.add(section);

        section = new PhoneNum("Bishal Bazar Ambulance Service","4244121");
        phoneArrayList.add(section);

        section = new PhoneNum("Blood Bank", "4225344");
        phoneArrayList.add(section);

        section = new PhoneNum("Metropolitan Police Range (Kathmandu) 1","4261945");
        phoneArrayList.add(section);

        section = new PhoneNum("Metropolitan Police Range (Kathmandu) 2","4261790");
        phoneArrayList.add(section);

        section = new PhoneNum("Metropolitan Police Range (Lalitpur)","5521207");
        phoneArrayList.add(section);

        section = new PhoneNum("Metropolitan Police Range (Bhaktapur)","6614821");
        phoneArrayList.add(section);

        section = new PhoneNum("Agrawal Sewa Centre","4424875");
        phoneArrayList.add(section);

        section = new PhoneNum("Aasara Drug Rehabilitation Center","4384881");
        phoneArrayList.add(section);

        section = new PhoneNum("Nepal Eye Bank","4493684");
        phoneArrayList.add(section);

        section = new PhoneNum("Nepal Eye Hospital","4250691");
        phoneArrayList.add(section);

        section = new PhoneNum("Tilganga Eye Hospital","4423684");
        phoneArrayList.add(section);

        section = new PhoneNum("Bir Hospital","4223807");
        phoneArrayList.add(section);

        section = new PhoneNum("Bir Hospital 2","4221988");
        phoneArrayList.add(section);

        section = new PhoneNum("Nepal Police Hospital","4412430");
        phoneArrayList.add(section);

        section = new PhoneNum("Nepal Police Hospital ","44122530");
        phoneArrayList.add(section);

        section = new PhoneNum("TU Teaching Hospital ","4412404");
        phoneArrayList.add(section);

        section = new PhoneNum("TU Teaching Hospital","4412505");
        phoneArrayList.add(section);

        section = new PhoneNum("Maternity Hospital","4253276");
        phoneArrayList.add(section);

        section = new PhoneNum("Teku Hospital","4253396");
        phoneArrayList.add(section);

        section = new PhoneNum("Patan Hospital","5522278");
        phoneArrayList.add(section);

        section = new PhoneNum("Patan Hospital","5522266");
        phoneArrayList.add(section);

        section = new PhoneNum("Bhaktapur Hospital","6610676");
        phoneArrayList.add(section);

        section = new PhoneNum("Mental Hospital","5521333");
        phoneArrayList.add(section);

        section = new PhoneNum("Kanti Children Hospital","4414798");
        phoneArrayList.add(section);

        section = new PhoneNum("Kanti Children Hospital","4427452");
        phoneArrayList.add(section);

        section = new PhoneNum("Kathmandu Model Hospital","4240805");
        phoneArrayList.add(section);

        section = new PhoneNum("B&B Hospital","5533206");
        phoneArrayList.add(section);

        section = new PhoneNum("Medicare National Hospital","4467067");
        phoneArrayList.add(section);

        section = new PhoneNum("Medicare National Hospital - Ambulance","4467067");
        phoneArrayList.add(section);

        section = new PhoneNum("Nepal Orthopaedic Hospital ","4493725");
        phoneArrayList.add(section);

        section = new PhoneNum("Kathmandu Medical College (Teaching Hospital - Sinamangal)","4476152");
        phoneArrayList.add(section);

        section = new PhoneNum("Nepal Medical College (Teaching Hospital - Jorpati) ","4486008");
        phoneArrayList.add(section);

        section = new PhoneNum("Kantipur Dental Hospital, Maharajgunj","4371603");
        phoneArrayList.add(section);

        section = new PhoneNum("Kantipur Hospital, New Baneshwor","4498757");
        phoneArrayList.add(section);

        section = new PhoneNum("Hospital and Research Centre","4476225");
        phoneArrayList.add(section);

        section = new PhoneNum("Norvic Hospital","4258554");
        phoneArrayList.add(section);

        section = new PhoneNum("Martyr Gangalal National Heart Centre","4371322");
        phoneArrayList.add(section);

        section = new PhoneNum("Martyr Gangalal National Heart Centre","4371374");
        phoneArrayList.add(section);

        section = new PhoneNum("Life Care Hospital","4227735");
        phoneArrayList.add(section);

        section = new PhoneNum("Miteri Hospital ","4280555");
        phoneArrayList.add(section);

        section = new PhoneNum("Miteri Hospital ","4222305");
        phoneArrayList.add(section);

        section = new PhoneNum("Capital Hospital","4244022");
        phoneArrayList.add(section);

        section = new PhoneNum("Shree Satya Sai Centre","4498035");
        phoneArrayList.add(section);

        section = new PhoneNum("Bhaktapur Redcross","6612266");
        phoneArrayList.add(section);

        section = new PhoneNum("National Kidney Centre ","4429866");
        phoneArrayList.add(section);

        section = new PhoneNum("National Kidney Centre ","4426016");
        phoneArrayList.add(section);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);

        context = getApplicationContext();
        listPhoneView = (ListView) findViewById(R.id.helpline);
        helplineListAdaptor = new HelplineListAdaptor(this, phoneArrayList);
        listPhoneView.setAdapter(helplineListAdaptor);

        listPhoneView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                String phNum = phoneArrayList.get(position).getNum();
                if (phNum.contentEquals("100")){

                } else {
                    phNum = "01"+phNum; //adding area code
                }

//                phNum = "+977"+phNum; //adding country zip code
                callIntent.setData(Uri.parse("tel:" + phNum));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });
        /*
        * listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.presentBookDetailFragment(mainActivity.getApplicationContext(),books[position]);
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_helpline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class HelplineListAdaptor extends BaseAdapter {

    private Context context;
    public ArrayList<PhoneNum> phoneArrayList;

    public HelplineListAdaptor (Context context, ArrayList<PhoneNum> phoneArrayList) {
        this.context = context;
        this.phoneArrayList = phoneArrayList;
    }

    @Override
    public int getCount() {
        return phoneArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return phoneArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_helpline,parent,false);
            vh = new ViewHolder();
            vh.iconImage = (ImageView) convertView.findViewById(R.id.iconImage);
            vh.title = (TextView) convertView.findViewById(R.id.helplineName);
            vh.phNum = (TextView) convertView.findViewById(R.id.helplineNum);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder)convertView.getTag();
        }
        vh.title.setText(phoneArrayList.get(position).getName());
        vh.phNum.setText(phoneArrayList.get(position).getNum());


        return convertView;
    }

    private class ViewHolder {
        ImageView iconImage;
        TextView title, phNum;
    }

    /*public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.custom_row_books, parent,false);
            vh = new ViewHolder();


            vh.mainImge = (ImageView) convertView.findViewById(R.id.imageViewCustomBook);
            vh.title = (TextView) convertView.findViewById(R.id.bookListTitle);
            vh.authorName = (TextView) convertView.findViewById(R.id.bookListAuthorName);
            vh.viewsNum = (TextView) convertView.findViewById(R.id.bookListViewsNum);
            vh.downloadNum = (TextView) convertView.findViewById(R.id.bookListDownloadNum);

            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder)convertView.getTag();
        }
        vh.title.setText(books[position].title);
        vh.mainImge.setImageResource(placeholder[0]);

        vh.title.setText(books[position].title);
//            new ImgLoad(context,mainImg).execute(books[position].coverImageURL);
            *//*ImageLoader imageLoader = new ImageLoader(context);*//*
            *//*imageLoader.DisplayImage(new ServerSideHelper(context).getBase_URL()+ServerSideHelper.BOOK_COVER_PIC+books[position].coverImageURL,vh.mainImge,null);*//*
        Picasso.with(context) //
                .load(new ServerSideHelper(context).getBase_URL()+ServerSideHelper.BOOK_COVER_PIC+books[position].coverImageURL) //
                .placeholder(placeholder[0]) //
                .fit() //
                .tag(context) //
                .into(vh.mainImge);

        vh.authorName.setText(books[position].author);
        vh.viewsNum.setText(""+books[position].views);
        vh.downloadNum.setText("" + books[position].downloads);

        return convertView;
    }*/

}

class PhoneNum {
    private String name;
    private String  number;

    public PhoneNum (String name, String  number){
        this.name = name;
        this.number = number;
    }

    public String getName () {
        return this.name;
    }

    public String  getNum () {
        return this.number;
    }

}