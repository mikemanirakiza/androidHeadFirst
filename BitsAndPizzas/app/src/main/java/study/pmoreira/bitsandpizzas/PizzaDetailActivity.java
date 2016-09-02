package study.pmoreira.bitsandpizzas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class PizzaDetailActivity extends Activity {

    public static final String EXTRA_PIZZA_ID = "pizzaId";

    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        int pizzaId = getIntent().getExtras().getInt(EXTRA_PIZZA_ID);

        TextView pizzaText = (TextView) findViewById(R.id.pizza_text);
        pizzaText.setText(Pizza.pizzas[pizzaId].getName());

        ImageView pizzaImage = (ImageView) findViewById(R.id.pizza_image);
        pizzaImage.setContentDescription(Pizza.pizzas[pizzaId].getName());
        pizzaImage.setImageDrawable(getResources().getDrawable(Pizza.pizzas[pizzaId].getImageResourceId()));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        TextView pizzaText = (TextView) findViewById(R.id.pizza_text);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, pizzaText.getText());

        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        shareActionProvider.setShareIntent(intent);
        shareActionProvider.setShareHistoryFileName(null);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                startActivity(new Intent(this, OrderActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
