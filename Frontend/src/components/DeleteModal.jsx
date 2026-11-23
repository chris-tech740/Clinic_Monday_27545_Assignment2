import React from 'react';
import { AlertCircle, Trash2, X } from 'lucide-react';

export default function DeleteModal({ office, isOpen, onClose, onConfirm, isDeleting }) {
  if (!isOpen || !office) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-dialog" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <div className="modal-icon-badge danger">
            <AlertCircle size={22} />
          </div>
          <div className="modal-title-group">
            <h3 className="modal-title">Delete Office</h3>
            <p className="modal-subtitle">This record will be permanently removed.</p>
          </div>
          <button type="button" className="modal-close-btn" onClick={onClose} disabled={isDeleting}>
            <X size={18} />
          </button>
        </div>

        <div className="modal-body">
          <p className="modal-message">
            Are you sure you want to delete <strong>Room {office.officeNumber} ({office.name})</strong>?
          </p>

          <div className="office-preview-card">
            <div className="preview-row">
              <span className="preview-label">Room Number</span>
              <span className="preview-value font-bold">Room {office.officeNumber}</span>
            </div>
            <div className="preview-row">
              <span className="preview-label">Office Name</span>
              <span className="preview-value">{office.name}</span>
            </div>
            {office.doctor && (
              <div className="preview-row doctor-warning">
                <span className="preview-label">Assigned Doctor</span>
                <span className="preview-value">
                  Dr. {office.doctor.firstName} {office.doctor.lastName}
                </span>
              </div>
            )}
          </div>
        </div>

        <div className="modal-footer">
          <button 
            type="button" 
            className="btn btn-secondary" 
            onClick={onClose} 
            disabled={isDeleting}
          >
            Cancel
          </button>
          <button 
            type="button" 
            className="btn btn-danger" 
            onClick={onConfirm} 
            disabled={isDeleting}
          >
            <Trash2 size={15} />
            <span>{isDeleting ? 'Deleting...' : 'Delete Office'}</span>
          </button>
        </div>
      </div>
    </div>
  );
}
