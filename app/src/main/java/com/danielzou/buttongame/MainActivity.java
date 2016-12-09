package com.danielzou.buttongame;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Set<MagicButton> mMagicButtons;
    Graph mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMagicButtons = new HashSet<>();

        // Game defaults to 4 nodes each of degree 1
        if (getIntent().getExtras() == null) {
            getIntent().putExtra("numNodes", 4);
            getIntent().putExtra("degreeNum", 1);
        }

        makeGame(getIntent().getIntExtra("numNodes", 4), getIntent().getIntExtra("degreeNum", 1));

        Set<Edge> edges = mGraph.getEdges();
        for (Edge edge : edges) {
            MagicButton mb = findMagicButtonById(edge.getNode1().getId());
            MagicButton otherMb = findMagicButtonById(edge.getNode2().getId());
            SwitchCompat otherSc = otherMb.getSwitchCompat();
            mb.appendToSwitchCompatLinks(otherSc);
        }

        for (final MagicButton magicButton : mMagicButtons) {
            magicButton.getSwitchCompat().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (SwitchCompat switchCompat : magicButton.getSwitchCompatLinks()) {
                        switchCompat.toggle();
                    }
                    if (isGameFinished()) {
                        TextView textView = (TextView) findViewById(R.id.text_view);
                        textView.setText("Congratulations - you win!");
                        for (MagicButton mb : mMagicButtons) {
                            final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                            mb.getSwitchCompat().startAnimation(myAnim);
                            mb.getSwitchCompat().setEnabled(false);
                        }
                    }
                }
            });
        }

    }

    /**
     * Finds a magic button in the set of magic buttons by id.
     * @param id Given id.
     * @return Corresponding magic button.
     */
    public MagicButton findMagicButtonById(int id) {
        for (MagicButton mb : mMagicButtons) {
            if (mb.getId() == id) {
                return mb;
            }
        }
        return null;
    }

    /**
     * Converts a dp value to a pixel value.
     * @return Corresponding pixel value.
     */
    public int dpsToPixels(int dps) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    /**
     * Adds a new switch compat to the grid layout.
     * @return The generated switch compat.
     */
    public SwitchCompat generateSwitchCompat() {
        GridLayout gl = (GridLayout) findViewById(R.id.grid_layout);
        SwitchCompat mySwitchCompat = new SwitchCompat(this);
        mySwitchCompat.setId(View.generateViewId());
        Drawable bg = getDrawable(R.drawable.magic_toggle_button);
        mySwitchCompat.setBackground(bg);
        mySwitchCompat.setElevation(dpsToPixels(2));
        mySwitchCompat.setChecked(true);
        Drawable thumb = getDrawable(R.drawable.invisible);
        mySwitchCompat.setThumbDrawable(thumb);
        Drawable track = getDrawable(R.drawable.invisible);
        mySwitchCompat.setTrackDrawable(track);
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
        lp.setGravity(Gravity.CENTER);
        lp.setMargins(dpsToPixels(8), dpsToPixels(8), dpsToPixels(8), dpsToPixels(8));
        gl.addView(mySwitchCompat, lp);
        return mySwitchCompat;
    }

    /**
     * Sets up the button game with a given number of nodes and arrows per node.
     * @param numButtons Given number of buttons in the game.
     * @param degree Desired number of one-way associations per button.
     */
    private void makeGame(int numButtons, int degree) {
        mGraph = new Graph(numButtons, degree);
        Set<Node> nodesSet = mGraph.getNodes();
        Node[] nodesArray = nodesSet.toArray(new Node[nodesSet.size()]);
        for (int i = 0; i < numButtons; i++) {
            final SwitchCompat sc = generateSwitchCompat();
            final MagicButton mb = new MagicButton(sc, nodesArray[i]);
            mMagicButtons.add(mb);
        }
    }

    /**
     * This method checks if all switch compats are off.
     * @return Whether the game is completed.
     */
    private boolean isGameFinished() {
        boolean isFinished = true;
        for (MagicButton mb : mMagicButtons) {
            if (mb.getSwitchCompat().isChecked()) {
                isFinished = false;
            }
        }
        return isFinished;
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
        if (id == R.id.action_restart) {
            finish();
            startActivity(getIntent());
        }
        if (id == R.id.action_add_button) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("numNodes", getIntent().getIntExtra("numNodes", 4) + 1);
            intent.putExtra("degreeNum", getIntent().getIntExtra("degreeNum", 1));
            startActivity(intent);
        }
        if (id == R.id.action_add_link) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("numNodes", getIntent().getIntExtra("numNodes", 4));
            intent.putExtra("degreeNum", getIntent().getIntExtra("degreeNum", 1) + 1);
            startActivity(intent);
        }
        if (id == R.id.action_remove_button) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("numNodes", getIntent().getIntExtra("numNodes", 4) - 1);
            intent.putExtra("degreeNum", getIntent().getIntExtra("degreeNum", 1));
            startActivity(intent);
        }
        if (id == R.id.action_remove_link) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("numNodes", getIntent().getIntExtra("numNodes", 4));
            intent.putExtra("degreeNum", getIntent().getIntExtra("degreeNum", 1) - 1);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
