package com.google.firebase.udacity.friendlychat;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import jpsam3hklam9.des.DESInterface;

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    private String text;
    private String chave_atual;
    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {

        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.chave_atual=new String("jf92j2ei,ad892dus,sidhd823");
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);

        FriendlyMessage message = getItem(position);

        boolean isPhoto = message.getPhotoUrl() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getPhotoUrl())
                    .into(photoImageView);
        } else {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);

            DESInterface d = new DESInterface();

            this.text=d.decifrar3DES(message.getText(),chave_atual);
            String resposta;

            if (this.text.length()==24){
                //atualizo a chave
                this.chave_atual=this.text;
                resposta="A nova chave chegou";
            }else{
                //mostra o texto que chegou
                resposta=" mensagem: "+this.text;
                //a chave volta a ser a padrao
                this.chave_atual="jf92j2ei,ad892dus,sidhd823";

            }

            messageTextView.setText(resposta);
        }
        authorTextView.setText(message.getName());

        return convertView;
    }

}

