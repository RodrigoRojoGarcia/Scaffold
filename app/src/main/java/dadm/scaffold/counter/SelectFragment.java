package dadm.scaffold.counter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;


public class SelectFragment extends BaseFragment {


    Button left;
    Button right;
    Button exit;
    ImageView image;


    int[] spaceships = {
            R.drawable.playershipblue,
            R.drawable.playershipgreen,
            R.drawable.playershipred,
            R.drawable.playeshiporange
    };

    private int selected = 0;

    public SelectFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select, container, false);



    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        left = (Button) view.findViewById(R.id.btn_left);
        right = (Button) view.findViewById(R.id.btn_right);

        exit = view.findViewById(R.id.btn_exit);

        image = (ImageView) view.findViewById(R.id.imageView);

        image.setImageDrawable(getResources().getDrawable(spaceships[selected]));

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected--;
                if(selected  < 0){
                    selected = spaceships.length-1;
                }
                image.setImageDrawable(getResources().getDrawable(spaceships[selected]));
                ((ScaffoldActivity)getActivity()).spaceshipselected = spaceships[selected];
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected++;
                if(selected >= spaceships.length){
                    selected = 0;
                }
                image.setImageDrawable(getResources().getDrawable(spaceships[selected]));
                ((ScaffoldActivity)getActivity()).spaceshipselected = spaceships[selected];
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScaffoldActivity)getActivity()).returnToMainMenu();
            }
        });

    }







}
