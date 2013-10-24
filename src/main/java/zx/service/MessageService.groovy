package zx.service

import zx.domain.Patient
import zx.domain.User

public interface MessageService {
    def send(Patient patient, String message, User doctor)
}