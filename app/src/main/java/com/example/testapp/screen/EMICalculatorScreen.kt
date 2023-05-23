package com.example.testapp.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.EMICalculatorViewModelLocal
import com.example.testapp.vm.EMICalculatorViewModel

@Composable
fun EMICalculatorScreen() {
    val emiCalculatorViewModel = viewModel<EMICalculatorViewModel>()
    CompositionLocalProvider(
        EMICalculatorViewModelLocal provides emiCalculatorViewModel
    ) {
        EmiCalculatorForm()
    }
}

@Composable
fun EmiCalculatorForm() {

    var vm = EMICalculatorViewModelLocal.current
    var principal by remember { mutableStateOf(0) }
    var rate by remember { mutableStateOf(0f) }
    var emi by remember { mutableStateOf(0) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        OutlinedTextField(
            value = principal.toString(),
            onValueChange = { principal = it.toInt() },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = rate.toString(),
            onValueChange = { rate = it.toFloat() },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = emi.toString(),
            onValueChange = { emi = it.toInt() },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row {
            Button(
                onClick = { vm.emiMonthsCalculate(principal = principal, rate = rate, emi = emi) },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 2.dp)
            ) {
                Text(text = "Calculate")
            }
            OutlinedButton(
                onClick = { vm.clearAll() },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 2.dp)
            ) {
                Text(text = "Clear")
            }

        }

        Column {
            DrawPieChart(progress = 75f)
        }

    }

}

@Composable
fun DrawPieChart(
    progress: Float,
    pathColor: Color = Color.LightGray,
    progressColor: Color = Color.DarkGray,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 22f
) {

    val animateFloat = remember { Animatable(0f) }
    LaunchedEffect(animateFloat) {
        animateFloat.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, easing = LinearEasing)
        )
    }

    Canvas(modifier = modifier, onDraw = {
        drawArc(
            color = pathColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(strokeWidth, cap = StrokeCap.Round),
            size = Size(size.width, size.height)
        )
        drawArc(
            color = progressColor,
            startAngle = 0f,
            sweepAngle = animateFloat.value * 360f * progress / 100,
            useCenter = false,
            style = Stroke(strokeWidth, cap = StrokeCap.Round),
            size = Size(size.width, size.height)
        )
    })
}
