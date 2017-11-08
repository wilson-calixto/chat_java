/**
 * Copyright Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.firebase.udacity.friendlychat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import jpsam3hklam9.des.DESInterface;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;


    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;

    private String mUsername;
    private String chave_atual=new String("jf92j2ei,ad892dus,sidhd823");

    private FirebaseDatabase mFirebaseDatabase;
    private String user1,user2,user3;
    private DatabaseReference mMessagesDatabaseReference;
    private DatabaseReference mMessagesDatabaseReference2;
    public static final String ANONYMOUS = "ju";
    private DatabaseReference mMessagesDatabaseReference3;
    private ChildEventListener mChildEventListener;

    private static Random rand = new Random();
    private static char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789".toCharArray();

    public String nomeAleatorio (int nCaracteres) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nCaracteres; i++){
            int ch = rand.nextInt (letras.length);
            sb.append (letras [ch]);
        }
        return sb.toString();
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = ANONYMOUS;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final String user1=new String("ju") ,user2=new String("wilson"),user3=new String("delrick");
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages/"+user1);
        mMessagesDatabaseReference2 = mFirebaseDatabase.getReference().child("messages/"+user2);
        mMessagesDatabaseReference3 = mFirebaseDatabase.getReference().child("messages/"+user3);


        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);

        // Initialize message ListView and its adapter
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //INICIA A CHAVE COM O VALOR PADRAO
                chave_atual="jf92j2ei,ad892dus,sidhd823";
                DESInterface d = new DESInterface();

                //recebe o nome dos destinatarios da mensagem e tranforma em uma lista
                String[] arrayDeStrings3 =mMessageEditText.getText().toString().split(",");

                UUID uuid = UUID.randomUUID();
                String myRandom = uuid.toString();
                //chave aleatoria
                String nova_chave =nomeAleatorio(8)+","+nomeAleatorio(8)+","+nomeAleatorio(8)+",";

                //nova_chave+="qe";
                //

                //CIFRA A NOVA CHAVE COM A CHAVE PADRAO
                String nova_chave_cifrada = d.cifrar3DES(nova_chave, chave_atual);
                FriendlyMessage friendlyMessage = new FriendlyMessage(nova_chave_cifrada, mUsername, null);

                //atualiza a chave
                chave_atual = nova_chave;

                //CIFRA A MENSAGEM COM A NOVA CHAVE
                String mensagem_cifrada = d.cifrar3DES(arrayDeStrings3[0],chave_atual);
                FriendlyMessage friendlyMessage2 = new FriendlyMessage(mensagem_cifrada, mUsername, null);


                //manda a nova chave cifrada pra todos que estao na lista de destinatarios
                //manda a MENSAGEM cifrada com a NOVA CHAVE pra todos que estao na lista de destinatarios
                for (int i=0;i<arrayDeStrings3.length;i++){

                        if (arrayDeStrings3[i].equals(user1)) {
                            mMessagesDatabaseReference.push().setValue(friendlyMessage);
                            mMessagesDatabaseReference.push().setValue(friendlyMessage2);
                        }

                        if (arrayDeStrings3[i].equals(user2)) {
                            mMessagesDatabaseReference2.push().setValue(friendlyMessage);
                            mMessagesDatabaseReference2.push().setValue(friendlyMessage2);
                        }

                        if (arrayDeStrings3[i].equals(user3)) {
                            mMessagesDatabaseReference3.push().setValue(friendlyMessage);
                            mMessagesDatabaseReference3.push().setValue(friendlyMessage2);
                        }
                }


                mMessageEditText.setText("");


            }
        });


        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});


        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);

                    mMessageAdapter.add(friendlyMessage);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



}
