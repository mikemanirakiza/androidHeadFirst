package study.pmoreira.workout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        WorkoutDetailFragment detailFragment = (WorkoutDetailFragment) getFragmentManager().findFragmentById(R.id.detail_frag);

        long workoutId = getIntent().getExtras().getLong(WorkoutDetailFragment.WORKOUT_ID);
        detailFragment.setWorkoutId(workoutId);
    }
}
