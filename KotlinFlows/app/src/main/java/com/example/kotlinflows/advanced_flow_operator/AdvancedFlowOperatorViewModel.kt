package com.example.kotlinflows.advanced_flow_operator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class AdvancedFlowOperatorViewModel : ViewModel() {


    private val flow1 = (1..10).asFlow().onEach { delay(1000L) }
    private val flow2 = (8 downTo 1).asFlow().onEach { delay(1000L) }

    private val isAuthed = MutableStateFlow(true)
    private val user = MutableStateFlow<User?>(null)
    private val posts = MutableStateFlow<List<Post>>(emptyList())


    private val _state = MutableStateFlow(ProfileState())


    val state: StateFlow<ProfileState>
        get() = _state

    init {


        // [combine] if any of those flows emits it will execute any of them
        // method 1
        isAuthed.combine(user) { authed, user ->

            if (authed) user else null
        }.combine(posts) { userOrNull, posts ->


            userOrNull?.let {
                _state.update {
                    it.copy(
                        profilePic = userOrNull.image,
                        posts = posts,
                        userName = userOrNull.name,
                    )
                }
            }

        }.launchIn(viewModelScope)

        // method 2

//        viewModelScope.launch {
//
//            user.combine(posts) { user, posts ->
//                _state.update {
//                    it.copy(
//                        profilePic = user?.image,
//                        posts = posts,
//                        userName = user?.name,
//                    )
//                }
//            }
//        }


        // it will wait for emit of the first flow and then second flow
        // they must both emit to execute
        flow1.zip(flow2) { less, great ->

            println(great - less)
        }.launchIn(viewModelScope)


        // puts all of the flows as one flow [of same type is better to do to not get a flow of <Any>]
        merge(flow2, flow1).onEach {
            println(it)
        }.launchIn(viewModelScope)

    }


}


data class ProfileState(
    val profilePic: String? = null,
    val userName: String? = null,
    val description: String? = null,
    val posts: List<Post> = emptyList(),
)

data class User(
    val name: String,
    val uid: String,
    val image: String
)

data class Post(
    val id: String,
    val data: String
)