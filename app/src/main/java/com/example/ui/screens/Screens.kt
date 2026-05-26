package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.ChatBubble,
                contentDescription = "App Logo",
                tint = Color.White,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Hal Chal",
                color = Color.White,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var phoneNumber by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to Hal Chal", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = acceptedTerms, onCheckedChange = { acceptedTerms = it })
            Text("I accept the Terms & Conditions and Privacy Policy")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onLoginSuccess,
            enabled = phoneNumber.isNotEmpty() && acceptedTerms,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login / Sign Up")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToChat: (String) -> Unit, onNavigateToSettings: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hal Chal", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(10) { index ->
                ChatListItem(
                    name = if (index % 3 == 0) "Family Group" else "User $index",
                    lastMessage = "Hey, how are you?",
                    time = "10:$index AM",
                    isGroup = index % 3 == 0,
                    isPremium = index % 2 != 0,
                    onClick = { onNavigateToChat("chat_$index") }
                )
            }
        }
    }
}

@Composable
fun ChatListItem(
    name: String,
    lastMessage: String,
    time: String,
    isGroup: Boolean,
    isPremium: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(if (isGroup) Color.LightGray else MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                if (isGroup) Icons.Default.Group else Icons.Default.Person,
                contentDescription = null,
                tint = if (isGroup) Color.DarkGray else MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(name, fontWeight = FontWeight.Bold)
                if (isPremium) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Premium",
                        tint = Color(0xFF1E88E5),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Text(lastMessage, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
        Text(time, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(chatId: String, onBack: () -> Unit) {
    var messageText by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Video Call */ }) { Icon(Icons.Default.Videocam, contentDescription = "Video Call") }
                    IconButton(onClick = { /* Voice Call */ }) { Icon(Icons.Default.Call, contentDescription = "Call") }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Attach */ }) {
                    Icon(Icons.Default.AttachFile, contentDescription = "Attach")
                }
                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp)),
                    placeholder = { Text("Type a message...") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                if (messageText.isNotEmpty()) {
                    IconButton(onClick = { messageText = "" }) {
                        Icon(Icons.Default.Send, contentDescription = "Send", tint = MaterialTheme.colorScheme.primary)
                    }
                } else {
                    IconButton(onClick = { /* Voice record */ }) {
                        Icon(Icons.Default.Mic, contentDescription = "Voice Message")
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFE5DDD5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                // Fake messages
                ChatBubble("Hello!", isMe = true, isViewOnce = false)
                ChatBubble("Hi, how are you?", isMe = false, isViewOnce = false)
                ChatBubble("This is a view once photo 📷 (Tap to view)", isMe = false, isViewOnce = true)
                ChatBubble("End to End Encrypted \uD83D\uDD12", isMe = true, isViewOnce = false)
            }
        }
    }
}

@Composable
fun ChatBubble(text: String, isMe: Boolean, isViewOnce: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isMe) Color(0xFFDCF8C6) else Color.White,
            modifier = Modifier.padding(vertical = 4.dp).widthIn(max = 250.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(8.dp),
                color = if (isViewOnce) Color.Gray else Color.Black,
                fontStyle = if (isViewOnce) androidx.compose.ui.text.font.FontStyle.Italic else null
            )
        }
    }
}

@Composable
fun StatusScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Status", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
            Box(modifier = Modifier.size(56.dp).clip(CircleShape).background(Color.LightGray))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("My Status", fontWeight = FontWeight.Bold)
                Text("Tap to add status update", style = MaterialTheme.typography.bodySmall)
            }
        }
        Divider()
        Text("Recent updates", modifier = Modifier.padding(16.dp), color = Color.Gray)
        // Dummy List
        LazyColumn {
            items(5) { index ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("User $index", fontWeight = FontWeight.Bold)
                        Text("10 mins ago", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun ReelsScreen() {
    val pagerState = androidx.compose.foundation.pager.rememberPagerState(pageCount = { 10 })
    
    androidx.compose.foundation.pager.VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) { page ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Fake Video Thumbnail / Background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF222222)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.PlayCircleOutline,
                        contentDescription = "Play",
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Reel Video $page", color = Color.White)
                }
            }

            // Overlay Details
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.Gray))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("User ${page + 1}", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("This is an amazing reel! #trending #viral", color = Color.White)
            }

            // Interaction Buttons
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 32.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var isLiked by remember { mutableStateOf(false) }
                IconButton(onClick = { isLiked = !isLiked }) {
                    Icon(
                        if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (isLiked) Color.Red else Color.White
                    )
                }
                Text("${(100..5000).random()}", color = Color.White, style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(16.dp))

                IconButton(onClick = { /* Comment */ }) {
                    Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Comment", tint = Color.White)
                }
                Text("${(10..500).random()}", color = Color.White, style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(16.dp))

                IconButton(onClick = { /* Share */ }) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                }
                Text("Share", color = Color.White, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun CallsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Calls", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
        LazyColumn {
            items(5) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color.LightGray))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("User $index", fontWeight = FontWeight.Bold)
                            Text("Today, 10:30 AM", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Icon(
                        if (index % 2 == 0) Icons.Default.Videocam else Icons.Default.Call,
                        contentDescription = "Call Type",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") }
            Text("Settings", style = MaterialTheme.typography.titleLarge)
        }
        
        ListItem(
            headlineContent = { Text("App Lock") },
            supportingContent = { Text("Secure app with Fingerprint/PIN") },
            leadingContent = { Icon(Icons.Default.Lock, null) }
        )
        ListItem(
            headlineContent = { Text("Chat Wallpaper & Fonts") },
            supportingContent = { Text("Customize your chat experience") },
            leadingContent = { Icon(Icons.Default.Wallpaper, null) }
        )
        ListItem(
            headlineContent = { Text("Google Drive Backup") },
            supportingContent = { Text("Last backup: Today") },
            leadingContent = { Icon(Icons.Default.Backup, null) }
        )
        Divider()
        ListItem(
            headlineContent = { Text("Get Premium (Blue Tick)") },
            supportingContent = { Text("Pay via UPI for the exclusive Mor Pankh badge") },
            leadingContent = { Icon(Icons.Default.Verified, null, tint = Color(0xFF1E88E5)) },
            modifier = Modifier.clickable { /* Simulate UPI */ }
        )
        Divider()
        ListItem(
            headlineContent = { Text("Admin Panel") },
            supportingContent = { Text("Manage 5TB Google Data Center Storage & Users") },
            leadingContent = { Icon(Icons.Default.AdminPanelSettings, null) },
            modifier = Modifier.clickable { /* Admin Panel */ }
        )
    }
}
