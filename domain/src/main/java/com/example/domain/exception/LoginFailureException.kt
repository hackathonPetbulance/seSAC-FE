package com.example.domain.exception

/**
 * 로그인 과정에서 발생하는 비즈니스 로직 예외
 */
class LoginFailureException(message: String) : LogicException(message)