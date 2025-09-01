import BaseService from "./BaseService";

class FeedbackService extends BaseService {
    constructor() {
        super("/api/feedbacks");
    }

    async findByAuthor(params = {}) {
        try {
            const response = this.api.get(`${this.endPoint}/given`, { params });
            return response;
        } catch (error) {
            console.error(error);
        }
    }

    async findByRecipient(params = {}) {
        try {
            const response = this.api.get(`${this.endPoint}/received`, { params });
            return response;
        } catch (error) {
            console.error(error);
        }
    }
}

export default FeedbackService;