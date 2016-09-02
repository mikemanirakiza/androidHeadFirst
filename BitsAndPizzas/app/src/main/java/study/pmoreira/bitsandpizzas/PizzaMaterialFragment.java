package study.pmoreira.bitsandpizzas;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PizzaMaterialFragment extends Fragment {

    /*
    private int[] pizzaImages;
    private String[] pizzaNames;
    */

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(Pizza.getPizzaNames(), Pizza.getPizzaImages());
        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(final int position) {
                Intent intent = new Intent(getActivity(), PizzaDetailActivity.class);
                intent.putExtra(PizzaDetailActivity.EXTRA_PIZZA_ID, position);
                getActivity().startActivity(intent);
            }
        });

        RecyclerView pizzaRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_pizza_material, container, false);
        pizzaRecycler.setAdapter(adapter);
        pizzaRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return pizzaRecycler;
    }


}
