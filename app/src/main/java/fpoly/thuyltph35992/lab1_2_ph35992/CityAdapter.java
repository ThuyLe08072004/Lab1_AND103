package fpoly.thuyltph35992.lab1_2_ph35992;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
    Context context;
    List<City> listCity;

    public CityAdapter(Context context,List<City> listCity) {
        this.listCity = listCity;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city,parent,false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City city = listCity.get(position);

        holder.txtName.setText(city.getName());
        holder.txtCountry.setText(city.getCountry());
        holder.txtCapital.setText(city.isCapital() ? "Là thủ đô" : "Không phải thủ đô");
        holder.txtPopulation.setText(String.valueOf(city.getPopulation()));
        holder.txtRegions.setText(String.valueOf(city.getRegions()));
        holder.txtState.setText(city.getState());
    }

    @Override
    public int getItemCount() {
        return listCity.size();
    }


    public static class CityViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtState, txtCountry, txtCapital, txtPopulation, txtRegions;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtState = itemView.findViewById(R.id.txtState);
            txtCountry = itemView.findViewById(R.id.txtCountry);
            txtCapital = itemView.findViewById(R.id.txtCapital);
            txtPopulation = itemView.findViewById(R.id.txtPopulation);
            txtRegions = itemView.findViewById(R.id.txtRegions);

        }
    }
}
