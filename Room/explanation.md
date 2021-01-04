1. annotation 대신 kapt를 사용해주어야 한다
2. plugins에 'kotlin-kapt'를 추가해주어야 한다

kotlin-android-extensions를 추가해줘야 findviewbyid를 사용 안 하고 바로 접근 가능

suspend가 붙은 아이들은 모두 코루틴 스코프 안에서 실행해야 한다.

코드 정리 : ctrl + Alt + l




class MainActivity : AppCompatActivity() {
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        /**
            * Livedata를 이용하기 위해서 lifecycleOwner 성정
            * 설정하지 않으면 livedata를 observe 할 때마다 xml이 refresh가 안 된다.
         **/
        binding.lifecycleOwner = this

        binding.viewmodel = mainViewModel

        // 기존에 사용하던 방법
        // val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Kotlin에서는 조금 더 편리하게 사용 가능
        // ViewModel을 선언해서 사용 가능
        mainViewModel.getAll().observe(this, Observer {
            result_text.text = it.toString()
        })

        add_button.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                mainViewModel.insert(Todo(todo_edit.text.toString()))
            }
        }
    }
}
