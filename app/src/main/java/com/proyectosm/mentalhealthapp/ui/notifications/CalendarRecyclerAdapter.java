package com.proyectosm.mentalhealthapp.ui.notifications;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proyectosm.mentalhealthapp.DatesUtils;
import com.proyectosm.mentalhealthapp.R;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.MyViewHolder> {

    CalendarModel[] calendar;
    Context context;
    ViewModelProvider notificationsViewModel;

    public CalendarRecyclerAdapter(Context context, CalendarModel[] calendar) {
        this.calendar = calendar;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.calendar_list_item, parent, false);

        return new MyViewHolder(view);
    }

    // Interpretamos la información de la lista e introducimos los elemntos en el calendario
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        String dateDay = new DatesUtils(calendar[position].date).getDay();

        holder.textViewDate.setText(String.valueOf(dateDay));
        holder.textViewColor.setBackgroundColor(Color.parseColor(calendar[position].color));

        // Remarca la entrada 'hoy' poniéndole un marco de color
        if(Integer.parseInt(dateDay)  == LocalDate.now().getDayOfMonth()) {
            holder.itemView.setBackground(ContextCompat.getDrawable(this.context, R.drawable.calendar_color_rectangle));
        } else {
            holder.itemView.setBackground(null);
        }

        // En función del sentiment analizado colocaría un texto
        if(calendar[position].sentiment_index == 0 && position + 1  < LocalDate.now().getDayOfMonth()) {
            holder.textViewColor.setText("Vacío");
        } else {
            holder.textViewColor.setText("");
        }

        // Al pulsar un box de una fecha inicia una ventana flotante dando información del día
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarModel CalendarEntry = calendar[position];

                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

                CalendarDetailBottomSheet sheet = new CalendarDetailBottomSheet(position, CalendarEntry);
                sheet.show(manager, "calender_detail_sheet");
            }
        });
    }

    @Override
    public int getItemCount() {
        return calendar.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewColor;
        View view;

        public MyViewHolder(@NonNull @NotNull View _view) {
            super(_view);

            view = _view;
            textViewDate = (TextView) _view.findViewById(R.id.cal_date);
            textViewColor = (TextView) _view.findViewById (R.id.cal_color);
        }
    }
}
