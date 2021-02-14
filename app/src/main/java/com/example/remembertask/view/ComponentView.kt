package com.example.remembertask.view

import android.content.Context
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remembertask.R
import com.example.remembertask.model.Task
import com.example.remembertask.model.Utils.OpenCreateTask
import com.example.remembertask.model.Utils.SetData
import com.google.android.material.switchmaterial.SwitchMaterial


@Composable
    fun TimeTask(context: Context){
        Column(modifier = Modifier.absolutePadding(left = 30.dp, top = 40.dp, right = 30.dp)) {
            var title = remember { mutableStateOf("") }
            Column(modifier = Modifier.fillMaxWidth()) {

                TextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    placeholder = { Text(
                            "Title", fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                        )
                    },
                    textStyle = MaterialTheme.typography.subtitle2,
                    backgroundColor = Color(0XFF17282C),
                    modifier = Modifier
                        .preferredHeight(22.dp),
                    activeColor = Color(0X0DFFAD),
                    )
            }
            var time = remember { mutableStateOf("") }
            Column(modifier = Modifier.width(70.dp)){
                Text(text = "Hours", fontSize = 25.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.absolutePadding(top = 20.dp, bottom = 5.dp),)
                TextField(
                    value = time.value,
                    onValueChange = { time.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = { Text("90", fontSize = 14.sp, fontWeight = FontWeight.SemiBold) },
                    textStyle = MaterialTheme.typography.subtitle2,
                    backgroundColor = Color(0XFF17282C),
                    modifier = Modifier.preferredWidth(40.dp),
                    maxLines = 1,
                    activeColor = Color(0X0DFFAD),
                )
            }
            var description = remember { mutableStateOf("") }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)){
                TextField(modifier = Modifier
                    .fillMaxWidth(),
                value = description.value,
                onValueChange = { description.value = it },
                    backgroundColor = Color(0XFF17282C),
                    placeholder = { Text(
                        "Description", fontSize = 14.sp, fontWeight = FontWeight.SemiBold,)
                    })
            }
            Button(onClick = {
                SetData(context = context,title = title.value.toString(), hours = time.value.toString(),
                    description = description.value.toString() )
            }) {
                Text(text = "Click me")
            }
        }

    }


    @Composable
    fun CardTask(task: Task){
        Column(modifier = Modifier.fillMaxWidth()) {
            var checked = remember { mutableStateOf(true) }

            var shape = RoundedCornerShape(8.dp)
            Card(modifier = Modifier
                .preferredHeight(135.dp)
                .fillMaxWidth(),
                backgroundColor = Color(3,31,37,180), shape = shape){
                Column(modifier = Modifier.padding(15.dp)) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .absolutePadding(right = 10.dp), horizontalArrangement = Arrangement.End) {
                        Switch(
                            colors = SwitchConstants.defaultColors(),
                            modifier = Modifier.preferredWidth(10.dp),
                            checked = checked.value,
                            onCheckedChange = { checked.value = it })
                    }
                    Text(text = task.Title.toString(), color = MaterialTheme.colors.primaryVariant,
                        fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "${task.Hours.toString()} : ${task.Minutes.toString()} ", fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.primary)

                }

            }
        }


    }


    @Composable
    fun CardAdd(onClick: () -> Unit){
        Column(modifier = Modifier.fillMaxWidth()) {
            var checked = remember { mutableStateOf(true) }

            var shape = RoundedCornerShape(8.dp)
            Card(
                modifier = Modifier
                    .preferredHeight(135.dp)
                    .fillMaxWidth()
                    .clickable(onClick =  onClick ),
                backgroundColor = Color(5, 37, 37), shape = shape
            ){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                        Icon(vectorResource(id = R.drawable.add), tint = Color(80, 255, 150))
                        Text(text = "Creat New", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    }
                }

            }
        }


    }

    @Composable
    fun Profile(){
        var image = imageResource(id = R.mipmap.goku)
        var imageshape = CircleShape
        Row(modifier = Modifier.absolutePadding(left = 30.dp)){
            Image(image, modifier = Modifier
                .preferredWidth(80.dp)
                .preferredHeight(80.dp)
                .clip(imageshape))
            Text(text = "Name",fontWeight = FontWeight.Bold,
                    modifier = Modifier.absolutePadding(top = 20.dp, left = 10.dp))
        }
    }
    
    @Composable
    fun Switch(){
        Row(modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(right = 20.dp), horizontalArrangement = Arrangement.End){
            var checked = remember { mutableStateOf(true) }
            Icon(vectorResource(id = R.drawable.brigthness), modifier = Modifier.absolutePadding(right = 18.dp),
            tint = Color(140,220,250))
            Switch(
                    colors = SwitchConstants.defaultColors(),
                    modifier = Modifier.preferredWidth(10.dp),
                    checked = checked.value,
                    onCheckedChange = { checked.value = it })
            Icon(vectorResource(id = R.drawable.brigthnees_sun),modifier = Modifier.absolutePadding(left = 18.dp),
                    tint = Color(200,200,0))
        }
    }

    @Composable
    fun Search(){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)){
            var shape = RoundedCornerShape(size = 8.dp)
            var search = remember { mutableStateOf("") }
            TextField(
                    shape = shape,
                    backgroundColor = Color(3,31,37,180),
                    value = search.value,
                    onValueChange = { search.value = it },
                    label = { Text("Search") },
                    modifier = Modifier.fillMaxWidth()
            )
        }
    }