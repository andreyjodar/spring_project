import BaseService from "./BaseService";

class UserService extends BaseService {
    constructor() {
        super("/api/users");
    }
}

export default UserService;