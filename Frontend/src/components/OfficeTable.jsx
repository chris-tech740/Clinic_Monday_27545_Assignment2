import React from 'react';
import { Trash2, Edit3, CheckCircle2, UserCheck } from 'lucide-react';

export default function OfficeTable({ 
  offices, 
  onDeleteClick, 
  onUpdateClick, 
  searchTerm, 
  onResetSearch 
}) {
  return (
    <div className="table-card">
      <div className="table-responsive">
        <table className="office-table">
          <thead>
            <tr>
              <th style={{ width: '90px' }}>ID</th>
              <th style={{ width: '160px' }}>Office #</th>
              <th>Office Name</th>
              <th>Status</th>
              <th style={{ width: '200px', textAlign: 'right' }}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {offices.length === 0 ? (
              <tr>
                <td colSpan="5" className="no-data-cell">
                  <div className="no-data-content">
                    <span className="no-data-text">No data</span>
                    {searchTerm && (
                      <button type="button" className="btn btn-secondary" onClick={onResetSearch} style={{ marginTop: '0.5rem' }}>
                        Clear Search
                      </button>
                    )}
                  </div>
                </td>
              </tr>
            ) : (
              offices.map((office) => {
                const isAssigned = !!office.doctor;
                return (
                  <tr key={office.id} className="table-row">
                    {/* ID */}
                    <td>
                      <span className="badge-id">#{office.id}</span>
                    </td>

                    {/* Office Number */}
                    <td>
                      <div className="room-badge">
                        <span className="room-label">Room</span>
                        <span className="room-number">{office.officeNumber}</span>
                      </div>
                    </td>

                    {/* Office Name */}
                    <td>
                      <div className="office-name-cell">
                        <span className="office-name-text">{office.name || 'Unnamed Office'}</span>
                      </div>
                    </td>

                    {/* Status / Assigned Doctor */}
                    <td>
                      {isAssigned ? (
                        <div className="status-pill status-assigned">
                          <UserCheck size={13} />
                          <span>
                            Dr. {office.doctor.firstName} {office.doctor.lastName}
                          </span>
                        </div>
                      ) : (
                        <div className="status-pill status-available">
                          <CheckCircle2 size={13} />
                          <span>Available</span>
                        </div>
                      )}
                    </td>

                    {/* Action Column with Update and Delete buttons */}
                    <td>
                      <div className="action-buttons">
                        {/* Update Button */}
                        <button
                          type="button"
                          className="btn-action btn-update"
                          onClick={() => onUpdateClick(office)}
                          title={`Update Office #${office.officeNumber}`}
                        >
                          <Edit3 size={14} />
                          <span>Update</span>
                        </button>

                        {/* Delete Button */}
                        <button
                          type="button"
                          className="btn-action btn-delete"
                          onClick={() => onDeleteClick(office)}
                          title={`Delete Office #${office.officeNumber}`}
                        >
                          <Trash2 size={14} />
                          <span>Delete</span>
                        </button>
                      </div>
                    </td>
                  </tr>
                );
              })
            )}
          </tbody>
        </table>
      </div>

      <div className="table-footer">
        <span className="table-count-text">
          Showing <strong>{offices.length}</strong> office record{offices.length !== 1 ? 's' : ''}
        </span>
      </div>
    </div>
  );
}
