package com.example.cholomanglicmot.nativechickenandduck;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.List;

public class ProjectAdapter extends BaseExpandableListAdapter {

    private Context ctx;
    private LinkedHashMap<String, List<String>> Project_Category;
    private List<String> Project_List;

    public ProjectAdapter(Context ctx, LinkedHashMap<String, List<String>> Project_Category, List<String> Project_List){
        this.ctx = ctx;
        this.Project_Category = Project_Category;
        this.Project_List = Project_List;
    }

    @Override
    public int getGroupCount() {
        return Project_List.size();
    }

    @Override
    public int getChildrenCount(int i) {

        return Project_Category.get(Project_List.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return Project_List.get(i);
    }

    @Override
    public Object getChild(int parent, int child) {

        return Project_Category.get(Project_List.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int parent, int child) {

        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertview, ViewGroup parentview) {
        String group_title = (String)getGroup(parent);
        if (convertview == null){
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview =  inflater.inflate(R.layout.parent_layout, parentview, false);
        }
        TextView parent_textview = (TextView)convertview.findViewById(R.id.parent_txt);
        ImageView parent_imageview = (ImageView)convertview.findViewById(R.id.parent_icon);
        parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setText(group_title);
        switch (group_title){
            case "Dashboard":
                parent_imageview.setImageResource(R.drawable.ic_dashboard_black_24dp);
                break;
            case "Pens":
                parent_imageview.setImageResource(R.drawable.ic_view_column_black_24dp);
                break;
            case "Generations and Lines":
                parent_imageview.setImageResource(R.drawable.ic_linear_scale_black_24dp);
                break;
            case "Families":
                parent_imageview.setImageResource(R.drawable.ic_add_to_photos_black_24dp);
                break;
            case "Breeders":
                parent_imageview.setImageResource(R.drawable.ic_stars_black_24dp);
                break;
            case "Replacements":
                parent_imageview.setImageResource(R.drawable.ic_sync_black_24dp);
                break;
            case "Brooders":
                parent_imageview.setImageResource(R.drawable.ic_child_friendly_black_24dp);
                break;
            case "Mortality, Culling, and Sales":
                parent_imageview.setImageResource(R.drawable.ic_trending_up_black_24dp);
                break;
            case "Reports":
                parent_imageview.setImageResource(R.drawable.ic_trending_up_black_24dp);
                break;
            case "Farm Settings":
                parent_imageview.setImageResource(R.drawable.ic_settings_black_24dp);
                break;
            case "Log Out":
                parent_imageview.setImageResource(R.drawable.ic_power_settings_new_black_24dp);
                break;
        }



        return convertview;
    }

    @Override
    public View getChildView(int parent, int child, boolean lastChild, View convertview, ViewGroup parentview) {
        String child_title = (String) getChild(parent, child);

        if (convertview == null){
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflater.inflate(R.layout.child_layout, parentview, false);
        }
        TextView child_textview = (TextView) convertview.findViewById(R.id.child_txt);
        child_textview.setText(child_title);

        return convertview;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
