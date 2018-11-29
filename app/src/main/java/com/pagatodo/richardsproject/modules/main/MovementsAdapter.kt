package com.pagatodo.richardsproject.modules.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pagatodo.network_manager.dtos.sender_yg.results.MovementsItemResult
import com.pagatodo.richardsproject.commons.StringUtils
import com.pagatodo.richardsproject.databinding.ItemMovementsBinding

class MovementsAdapter(val items: List<MovementsItemResult>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemMovementsBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class ViewHolder(val binding: ItemMovementsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MovementsItemResult) {
        with(binding) {
            txtItemMovDate.text = item.fechaMovimiento.split(" ")[0]
            txtItemMovMonth.text = item.fechaMovimiento.split(" ")[1]
            if (item.idTipoTransaccion != 14) {
                txtTitleDetail.text = item.descripcion
                txtSubtitleDetail.text = item.detalle
            } else {
                txtTitleDetail.text = item.detalle
                txtSubtitleDetail.text = item.concepto
            }
            txtAmount.text = StringUtils.getCurrencyValue(item.total.toDouble())
        }
    }
}