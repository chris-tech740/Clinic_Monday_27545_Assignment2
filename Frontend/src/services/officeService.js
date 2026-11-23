import axios from 'axios';

// Connect directly to Spring Boot backend running on port 2028
const API_BASE_URL = 'http://localhost:2028/api/offices';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json, text/plain, */*',
  },
  timeout: 8000,
});

export const officeService = {
  /**
   * Fetch all registered offices from the backend database
   * Endpoint: GET /api/offices/all
   */
  async getAllOffices() {
    try {
      const response = await api.get('/all');
      return { 
        success: true, 
        data: Array.isArray(response.data) ? response.data : [] 
      };
    } catch (err) {
      console.error('API Error [getAllOffices]:', err);
      const errMsg = err.response?.data || err.message || 'Cannot connect to backend server';
      return { 
        success: false, 
        error: typeof errMsg === 'string' ? errMsg : 'Failed to fetch offices from database' 
      };
    }
  },

  /**
   * Fetch free/available offices from the backend database
   * Endpoint: GET /api/offices/free
   */
  async getFreeOffices() {
    try {
      const response = await api.get('/free');
      return { 
        success: true, 
        data: Array.isArray(response.data) ? response.data : [] 
      };
    } catch (err) {
      console.error('API Error [getFreeOffices]:', err);
      return { success: false, error: err.message };
    }
  },

  /**
   * Fetch a single office by ID
   * Endpoint: GET /api/offices/{id}
   */
  async getOfficeById(id) {
    try {
      const response = await api.get(`/${id}`);
      return { success: true, data: response.data };
    } catch (err) {
      return { success: false, error: err.response?.data || err.message };
    }
  },

  /**
   * Delete an office by ID from the database
   * Endpoint: DELETE /api/offices/delete/{id}
   */
  async deleteOffice(id) {
    try {
      const response = await api.delete(`/delete/${id}`);
      return { 
        success: true, 
        message: typeof response.data === 'string' ? response.data : 'Office is deleted Successfully' 
      };
    } catch (err) {
      console.error('API Error [deleteOffice]:', err);
      const errMsg = err.response?.data || err.message;
      return { 
        success: false, 
        error: typeof errMsg === 'string' ? errMsg : 'Failed to delete office' 
      };
    }
  },

  /**
   * Save a new office to the database
   * Endpoint: POST /api/offices/save
   */
  async saveOffice(officeData) {
    try {
      const response = await api.post('/save', {
        name: officeData.name,
        officeNumber: parseInt(officeData.officeNumber, 10),
      });
      return { 
        success: true, 
        message: typeof response.data === 'string' ? response.data : 'Office is saved Successfully',
        data: response.data 
      };
    } catch (err) {
      console.error('API Error [saveOffice]:', err);
      const errMsg = err.response?.data || err.message;
      return { 
        success: false, 
        error: typeof errMsg === 'string' ? errMsg : 'Failed to save office' 
      };
    }
  },
};
