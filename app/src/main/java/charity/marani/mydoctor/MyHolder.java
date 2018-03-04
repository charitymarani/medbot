package charity.marani.mydoctor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by user on 2/27/2018.
 */
public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   //our views
    TextView titletxt;
    TextView introtxt;
    TextView categorytxt;

    private HandleCallBack handleCallBack;


    //our constructor
    public MyHolder(View itemView) {
        super(itemView);
        titletxt=(TextView) itemView.findViewById(R.id.condition_title);
        introtxt=(TextView) itemView.findViewById(R.id.condition_intro);
        categorytxt=(TextView) itemView.findViewById(R.id.condition_category);

        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        this.handleCallBack.loadDetails(getLayoutPosition());
    }

    public void setItemClickListener(HandleCallBack ic){

        this.handleCallBack=ic;
    }
}
