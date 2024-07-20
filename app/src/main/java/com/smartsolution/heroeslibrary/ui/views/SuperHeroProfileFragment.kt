package com.smartsolution.heroeslibrary.ui.views

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.smartsolution.heroeslibrary.data.repository.core.PowerStatsResponse
import com.smartsolution.heroeslibrary.data.repository.core.SuperHeroDetailResponse
import com.smartsolution.heroeslibrary.databinding.FragmentSuperHeroProfileBinding
import com.smartsolution.heroeslibrary.ui.viewmodel.SuperHeroViewModel
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class SuperHeroProfileFragment : Fragment() {

    private var _binding: FragmentSuperHeroProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SuperHeroViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuperHeroProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.waitDownload.observe(viewLifecycleOwner){
            if(it == false){
                viewModel.superHeroitem.observe(viewLifecycleOwner) { superHero ->
                    createUI(superHero)
                }
            }
        }

    }

    private fun createUI(superhero: SuperHeroDetailResponse) {
        binding.apply {
            Picasso.get().load(superhero.image.url).into(binding.ivSuperhero)
            tvSuperheroName.text = superhero.name
            prepareStats(superhero.powerstats)
            tvSuperheroRealName.text = superhero.biography.fullName
            tvPublisher.text = superhero.biography.publisher
            tvOcupation.text = superhero.work.occupation
            tvLocation.text = superhero.work.base
        }
    }

    private fun prepareStats(powerstats: PowerStatsResponse) {
        binding.apply {
            updateHeight(viewCombat, powerstats.combat)
            updateHeight(viewDurability, powerstats.durability)
            updateHeight(viewSpeed, powerstats.speed)
            updateHeight(viewStrength, powerstats.strength)
            updateHeight(viewIntelligence, powerstats.intelligence)
            updateHeight(viewPower, powerstats.power)
        }
    }

    private fun updateHeight(view: View, stat: String) {
        val heightInDp = try {
            stat.toFloatOrNull() ?: 0f
        } catch (e: NumberFormatException) {
            0f
        }
        val params = view.layoutParams
        params.height = pxToDp(heightInDp)
        view.layoutParams = params
    }

    private fun pxToDp(px: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)
            .roundToInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}