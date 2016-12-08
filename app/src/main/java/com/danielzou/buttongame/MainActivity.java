package com.danielzou.buttongame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Set<MagicButton> mMagicButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwitchCompat sc1 = (SwitchCompat) findViewById(R.id.button1);
        final SwitchCompat sc2 = (SwitchCompat) findViewById(R.id.button2);
        final SwitchCompat sc3 = (SwitchCompat) findViewById(R.id.button3);
        final SwitchCompat sc4 = (SwitchCompat) findViewById(R.id.button4);

        Graph graph = new Graph(4, 1);
        Set<Node> nodesSet = graph.getNodes();
        Node[] nodesArray = nodesSet.toArray(new Node[nodesSet.size()]);
        final MagicButton mb1 = new MagicButton(sc1, nodesArray[0]);
        final MagicButton mb2 = new MagicButton(sc2, nodesArray[1]);
        final MagicButton mb3 = new MagicButton(sc3, nodesArray[2]);
        final MagicButton mb4 = new MagicButton(sc4, nodesArray[3]);
        mMagicButtons = new HashSet<MagicButton>();
        mMagicButtons.add(mb1);
        mMagicButtons.add(mb2);
        mMagicButtons.add(mb3);
        mMagicButtons.add(mb4);

        Set<Edge> edges = graph.getEdges();
        for (Edge edge : edges) {
            MagicButton mb = findMagicButtonById(edge.getNode1().getId());
            //SwitchCompat sc = mb.getSwitchCompat();
            MagicButton otherMb = findMagicButtonById(edge.getNode2().getId());
            SwitchCompat otherSc = otherMb.getSwitchCompat();
            mb.appendToSwitchCompatLinks(otherSc);
            //otherMb.appendToSwitchCompatLinks(sc);
        }

        for (final MagicButton magicButton : mMagicButtons) {
            magicButton.getSwitchCompat().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (SwitchCompat switchCompat : magicButton.getSwitchCompatLinks()) {
                        switchCompat.toggle();
                    }
                }
            });
        }

    }

    public MagicButton findMagicButtonById(int id) {
        for (MagicButton mb : mMagicButtons) {
            if (mb.getId() == id) {
                return mb;
            }
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
