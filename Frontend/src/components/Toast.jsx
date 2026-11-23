import React from 'react';
import { CheckCircle2, AlertCircle, X } from 'lucide-react';

export default function Toast({ toast, onClose }) {
  if (!toast) return null;

  const { type, message } = toast;

  return (
    <div className={`toast-container toast-${type}`}>
      <div className="toast-content">
        {type === 'error' ? (
          <AlertCircle size={18} style={{ color: 'var(--danger)' }} />
        ) : (
          <CheckCircle2 size={18} className="toast-icon" />
        )}
        <span className="toast-message">{message}</span>
      </div>
      <button type="button" className="toast-close" onClick={onClose} title="Dismiss">
        <X size={14} />
      </button>
    </div>
  );
}
