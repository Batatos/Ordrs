package com.example.jorjborj.ordrs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.DecimalFormat;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CashFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CashFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CashFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CashFragment newInstance(String param1, String param2) {
        CashFragment fragment = new CashFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cash, container, false);

        ImageButton clear = (ImageButton)view.findViewById(R.id.clear);
        final TextView amount = (TextView)view.findViewById(R.id.amount);
        LinearLayout twohundred,onehundred,fifty,twenty,ten,five,two,one,half,agora;
        Button pay,cancel;

        twohundred = (LinearLayout)view.findViewById(R.id.twohundred);
        onehundred = (LinearLayout)view.findViewById(R.id.onehundred);
        fifty = (LinearLayout)view.findViewById(R.id.fifty);
        twenty = (LinearLayout)view.findViewById(R.id.twenty);
        ten = (LinearLayout)view.findViewById(R.id.ten);
        five = (LinearLayout)view.findViewById(R.id.five);
        two = (LinearLayout)view.findViewById(R.id.two);
        one = (LinearLayout)view.findViewById(R.id.one);
        half = (LinearLayout)view.findViewById(R.id.half);
        agora = (LinearLayout)view.findViewById(R.id.agora);
        pay = (Button)view.findViewById(R.id.payBtn);
        cancel = (Button)view.findViewById(R.id.cancelBtn);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("0.0");
            }
        });

        twohundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double x = Double.parseDouble(amount.getText().toString()) + 200.0;
                String xString = Double.toString(x);
                amount.setText(xString);
            }
        });

        onehundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double x = Double.parseDouble(amount.getText().toString()) + 100.0;
                String xString = Double.toString(x);
                amount.setText(xString);
            }
        });

        fifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double x = Double.parseDouble(amount.getText().toString()) + 50.0;
                String xString = Double.toString(x);
                amount.setText(xString);
            }
        });

        twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double x = Double.parseDouble(amount.getText().toString()) + 20.0;
                String xString = Double.toString(x);
                amount.setText(xString);
            }
        });

        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double x = Double.parseDouble(amount.getText().toString()) + 10.0;
                String xString = Double.toString(x);
                amount.setText(xString);
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double x = Double.parseDouble(amount.getText().toString()) + 5.0;
                String xString = Double.toString(x);
                amount.setText(xString);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double x = Double.parseDouble(amount.getText().toString()) + 2.0;
                String xString = Double.toString(x);
                amount.setText(xString);
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double x = Double.parseDouble(amount.getText().toString()) + 1.0;
                String xString = Double.toString(x);
                amount.setText(xString);
            }
        });

        half.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double x = Double.parseDouble(amount.getText().toString()) + 0.5;
                String xString = Double.toString(x);
                amount.setText(xString);
            }
        });

        agora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double x = Double.parseDouble(amount.getText().toString()) + 0.1;
                String xString = Double.toString(x);
                amount.setText(xString);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Double total = Double.parseDouble(getActivity().getIntent().getExtras().get("total").toString());
                final Double received = Double.parseDouble(amount.getText().toString());
                final Double returnToCustomer = Double.parseDouble((new java.text.DecimalFormat("##.##").format(received-total)));

                if(total>received){
                    Toast.makeText(getContext(), "Not Enough.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Return to Customer: " + returnToCustomer, Toast.LENGTH_SHORT).show();

                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

                    alertDialog.setTitle("Thank you!");
                    alertDialog.setMessage("Return to Customer: ₪" + returnToCustomer);
                    alertDialog.setCanceledOnTouchOutside(false);

                    alertDialog.setButton(AlertDialog.BUTTON1, "Finish",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseHelper db = new DatabaseHelper(getContext());
                                    db.getWritableDatabase();
                                    db.deleteOrderByTableNum(Integer.parseInt(getActivity().getIntent().getExtras().get("tablenum").toString()));
                                    db.deleteOrderItemsByTable(Integer.parseInt(getActivity().getIntent().getExtras().get("tablenum").toString()));
                                    Intent i = new Intent(getActivity(), PickOptionActivity.class);
                                    startActivity(i);
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON2, "Send Receipt",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseHelper db = new DatabaseHelper(getContext());
                                    db.getWritableDatabase();
                                    String subject, message = "";
                                    int counter = 0;

                                    subject = "Ordrs App - Receipt on Table " + Integer.parseInt(getActivity().getIntent().getExtras().get("tablenum").toString());

                                    Cursor c = db.getOrderItemsByTableNum(Integer.parseInt((String) getActivity().getIntent().getExtras().get("tablenum")));
                                    message+="------------------------------------\n";
                                    message+="--------  SUMMARY  -------\n";
                                    message+="------------------------------------\n";

                                    try {
                                        counter = 0;
                                        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                                            message += c.getString(c.getColumnIndex("quantity"));
                                            message += "x ";
                                            message += c.getString(c.getColumnIndex("name"));
                                            message += "\n";
                                            counter += c.getInt(c.getColumnIndex("price"));
                                        }

                                    } catch (Throwable t) {
                                        t.printStackTrace();
                                    }

                                    double t = total - ((total*Integer.parseInt((String)getActivity().getIntent().getExtras().get("ahoz")))/100);
                                    double t1 = Double.parseDouble((new java.text.DecimalFormat("##.##").format(t)));

                                    message+="==================\n";
                                    message += "Price: ₪" + total +"\n";
                                    message += "Discount: " + getActivity().getIntent().getExtras().get("ahoz") +"%\n\n";
                                    message += "Total Price: ₪" + t1+"\n";

                                    message+="==================\n\n";
                                    message+="Thank you for choosing Ordrs Application.\n";
                                    message+="For any issues please call +972-545-983-177.";

                                    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                                    emailIntent.setType("text/plain");
                                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);

                                    emailIntent.setType("message/rfc822");

                                    try {
                                        Intent i = new Intent(getContext(),PickOptionActivity.class);
                                        getActivity().finish();
                                        startActivity(i);
                                        startActivity(Intent.createChooser(emailIntent,
                                                "Send email using..."));
                                    } catch (android.content.ActivityNotFoundException ex) {
                                        Toast.makeText(getActivity(),
                                                "No email clients installed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    db.deleteOrderByTableNum(Integer.parseInt(getActivity().getIntent().getExtras().get("tablenum").toString()));
                                    db.deleteOrderItemsByTable(Integer.parseInt(getActivity().getIntent().getExtras().get("tablenum").toString()));

                                }
                            });

                    alertDialog.show();
                    final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.cashregister);
                    mp.start();
                    TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                    textView.setTextSize(25);

                }
            }
        });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
