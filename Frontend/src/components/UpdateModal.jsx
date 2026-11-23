import React from 'react';
import { Clock, X } from 'lucide-react';

export default function UpdateModal({ office, isOpen, onClose }) {
  if (!isOpen || !office) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-dialog not-yet-dialog" onClick={(e) => e.stopPropagation()}>
        <button 
          type="button" 
          className="not-yet-close-btn" 
          onClick={onClose} 
          title="Close"
        >
          <X size={18} />
        </button>

        <div className="not-yet-content">
          <div className="not-yet-icon-wrapper">
            <Clock size={36} />
          </div>

          <h2 className="not-yet-title">Not Yet</h2>

          <p className="not-yet-message">
            Update functionality is not yet available.
          </p>

          <div className="not-yet-actions">
            <button 
              type="button" 
              className="btn btn-primary not-yet-btn" 
              onClick={onClose}
            >
              Back to Offices
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
