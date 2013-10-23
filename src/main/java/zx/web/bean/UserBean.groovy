package zx.web.bean

import zx.domain.User
import zx.service.Services

class UserBean {
    Long id
    String username
    String displayName
    Long supervisorId
    String supervisorName
    String supervisorDisplayName
    String password

    UserBean() {

    }

    UserBean(User user) {
        if (user) {
            id = user.id
            username = user.username
            displayName = user.displayName
            supervisorId = user.supervisor?.id
            supervisorName = user.supervisor?.username?: ''
            supervisorDisplayName = user.supervisor?.displayName?: ''
        }
    }

    def toUser() {
        def userService = Services.userService()
        def user = id ? userService.findById(id) : new User()
        user.username = username
        user.displayName = displayName
        user.supervisor = userService.findById(supervisorId)
        if (password) {
            user.password = password
        }
        user
    }
}
