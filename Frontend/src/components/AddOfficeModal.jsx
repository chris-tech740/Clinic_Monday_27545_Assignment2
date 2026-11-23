import React, { useState } from 'react';
import { Plus, X, AlertCircle } from 'lucide-react';

export default function AddOfficeModal({ isOpen, onClose, onSave, isSaving }) {
  const [formData, setFormData] = useState({
    name: '',
    officeNumber: '',
  });
  const [formError, setFormError] = useState('');

  if (!isOpen) return null;

  const handleSubmit = (e) => {
    e.preventDefault();
    setFormError('');

    const name = formData.name.trim();
    const officeNum = parseInt(formData.officeNumber, 10);

    if (!officeNum || isNaN(officeNum) || officeNum <= 0) {
      setFormError('Please enter a valid positive room number.');
      return;
    }

    if (!name) {
      setFormError('Office name is required.');
      return;
    }

    onSave({ name, officeNumber: officeNum });
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-dialog" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <div className="modal-icon-badge primary">
            <Plus size={22} />
          </div>
          <div className="modal-title-group">
            <h3 className="modal-title">New Office</h3>
            <p className="modal-subtitle">Register a new room in the clinic</p>
          </div>
          <button type="button" className="modal-close-btn" onClick={onClose} disabled={isSaving}>
            <X size={18} />
          </button>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="modal-body">
            {formError && (
              <div className="form-error-alert">
                <AlertCircle size={15} />
                <span>{formError}</span>
              </div>
            )}

            <div className="form-group">
              <label htmlFor="officeNumber" className="form-label">
                Room Number <span className="required">*</span>
              </label>
              <input
                id="officeNumber"
                type="number"
                min="1"
                className="form-input"
                placeholder="e.g. 105"
                value={formData.officeNumber}
                onChange={(e) => setFormData({ ...formData, officeNumber: e.target.value })}
                required
                autoFocus
              />
            </div>

            <div className="form-group">
              <label htmlFor="officeName" className="form-label">
                Office Name <span className="required">*</span>
              </label>
              <input
                id="officeName"
                type="text"
                className="form-input"
                placeholder="e.g. Consultation Room, Pediatrics"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                required
              />
            </div>
          </div>

          <div className="modal-footer">
            <button 
              type="button" 
              className="btn btn-secondary" 
              onClick={onClose} 
              disabled={isSaving}
            >
              Cancel
            </button>
            <button 
              type="submit" 
              className="btn btn-primary" 
              disabled={isSaving}
            >
              <span>{isSaving ? 'Saving...' : 'Save Office'}</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
