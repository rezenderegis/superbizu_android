package com.bizu.question.service.questions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bizu.R;
import com.bizu.question.Question;

import java.util.List;

/**
 * Created by fabricio on 1/6/16.
 */
public class QuestoesAdapter extends ArrayAdapter<Question> {

    static final int LAYOUT = R.layout.lista_questoes;

    public QuestoesAdapter(Context context,
                           List<Question> objects) {

        super(context, LAYOUT, objects);
    }

    @Override
    public View getView(int position,
                        View convertView, ViewGroup parent) {

        Context ctx = parent.getContext();
        if (convertView == null){
            convertView = LayoutInflater.from(ctx)
                    .inflate(R.layout.lista_questoes, null);
        }

        TextView idQuestao = (TextView) convertView.findViewById(R.id.idQuestao);

        TextView txt = (TextView)
                convertView.findViewById(R.id.txtName);
        Question questoes = getItem(position);
        txt.setText(questoes.getDescription());
        idQuestao.setText(questoes.getId().toString());

        return convertView;
    }
}
