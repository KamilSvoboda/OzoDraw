package eu.kamilsvoboda.ozodraw;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommandListRecyclerViewAdapter extends RecyclerView.Adapter<CommandListRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<CommandsActivity.Command> mValues;
    private final ICommandListItemListener mListener;
    private Context mContext;

    public CommandListRecyclerViewAdapter(ArrayList<CommandsActivity.Command> items, ICommandListItemListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_commands_list_item, parent, false);
        mContext = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mData = mValues.get(position);

        CommandsActivity.Command command = mValues.get(position);
        holder.commandButton.setText(command.getTitle());

        holder.commandButton.setCompoundDrawablesWithIntrinsicBounds(
                null,
                new BitmapDrawable(mContext.getResources(), CommandBitmapBuilder.getCommandBitmap(command.getColors())),
                null,
                null);

        holder.commandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.commandClicked(holder.mData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ICommandListItemListener {
        void commandClicked(CommandsActivity.Command command);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CommandsActivity.Command mData;
        View mView;
        @BindView(R.id.command_button)
        Button commandButton;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }

}
