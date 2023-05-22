package com.example.testapp.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testapp.MainViewModelLocal
import com.example.testapp.NavHostControllerLocal
import com.example.testapp.Screen

@Composable
fun DashboardScreen() {
    val mainViewModel = MainViewModelLocal.current
    val navController = NavHostControllerLocal.current
    LazyColumn(modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)) {
        item {
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onClick = {
                    mainViewModel.navigateTo(navController, Screen.EMICalculator)
                }) {
                Text(text = "EMI Calculator")
            }
        }
    }
}