package com.example.leica.bcr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public
class MySimpleAdapter extends SimpleAdapter {

    private LayoutInflater inflater;
    private List<? extends Map<String, ?>> listData;

    public class ViewHolder {
        TextView line1;
        TextView line2;
    }

    public MySimpleAdapter( Context context, List<? extends Map<String, ?> > data, int resource, String[] from, int[] to ) {
        super(context, data, resource, from, to);

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listData = data;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        View view = convertView;        // ビューを受け取る

        if (view == null) {
            view = inflater.inflate(R.layout.row, parent, false);

            holder = new ViewHolder();
            holder.line1 = (TextView) view.findViewById(R.id.nameText1);
            holder.line2 = (TextView) view.findViewById(R.id.nameText2);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        String name, sub, sex, company, department, post, tell, mobile, fax, mail, url, tags;

        name = listData.get(position).get("name").toString();
        sub = listData.get(position).get("sub").toString();
        sex = listData.get(position).get("sex").toString();
        company = listData.get(position).get("company").toString();
        department = listData.get(position).get("department").toString();
        post = listData.get(position).get("post").toString();
        tell = listData.get(position).get("tell").toString();
        mobile = listData.get(position).get("mobile").toString();
        fax = listData.get(position).get("fax").toString();
        mail = listData.get(position).get("mail").toString();
        url = listData.get(position).get("url").toString();
        tags = listData.get(position).get("tags").toString();

        holder.line1.setText( name );
        holder.line2.setText( sub );


        return view;
    }

}

