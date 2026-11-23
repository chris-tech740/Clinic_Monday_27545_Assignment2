import React, { useState, useEffect, useMemo, useCallback } from 'react';
import Navbar from './components/Navbar';
import StatsCards from './components/StatsCards';
import SearchBar from './components/SearchBar';
import OfficeTable from './components/OfficeTable';
import DeleteModal from './components/DeleteModal';
import UpdateModal from './components/UpdateModal';
import AddOfficeModal from './components/AddOfficeModal';
import Toast from './components/Toast';
import { officeService } from './services/officeService';
import { Loader2 } from 'lucide-react';
import './App.css';

export default function App() {
  const [offices, setOffices] = useState([]);
  const [loading, setLoading] = useState(true);
  const [toast, setToast] = useState(null);

  // Search & Filter State
  const [searchTerm, setSearchTerm] = useState('');
  const [activeFilter, setActiveFilter] = useState('all'); // 'all' | 'available' | 'assigned'

  // Modals State
  const [deleteModal, setDeleteModal] = useState({
    isOpen: false,
    office: null,
    isDeleting: false,
  });

  const [updateModal, setUpdateModal] = useState({
    isOpen: false,
    office: null,
  });

  const [addModal, setAddModal] = useState({
    isOpen: false,
    isSaving: false,
  });

  // Auto-dismiss toast
  const showToast = useCallback((type, message) => {
    setToast({ type, message });
    setTimeout(() => {
      setToast((current) => (current?.message === message ? null : current));
    }, 4000);
  }, []);

  // Fetch offices directly from backend API
  const fetchOffices = useCallback(async () => {
    setLoading(true);

    const result = await officeService.getAllOffices();

    if (result.success && Array.isArray(result.data)) {
      setOffices(result.data);
    } else {
      setOffices([]);
    }

    setLoading(false);
  }, []);

  useEffect(() => {
    fetchOffices();
  }, [fetchOffices]);

  // Search feature by office name OR office number
  const filteredOffices = useMemo(() => {
    const query = searchTerm.trim().toLowerCase();

    return offices.filter((office) => {
      // 1. Search criteria: match by name OR match by officeNumber
      const name = (office.name || '').toLowerCase();
      const numberStr = (office.officeNumber ?? '').toString();

      const matchesSearch =
        !query ||
        name.includes(query) ||
        numberStr.includes(query);

      if (!matchesSearch) return false;

      // 2. Status filter
      if (activeFilter === 'available') return !office.doctor;
      if (activeFilter === 'assigned') return !!office.doctor;

      return true;
    });
  }, [offices, searchTerm, activeFilter]);

  // Stats
  const totalCount = offices.length;
  const availableCount = offices.filter((o) => !o.doctor).length;
  const occupiedCount = offices.filter((o) => !!o.doctor).length;

  // Handle Delete button click in Action column
  const handleDeleteClick = (office) => {
    setDeleteModal({
      isOpen: true,
      office,
      isDeleting: false,
    });
  };

  // Confirm Delete execution - directly on backend database
  const handleConfirmDelete = async () => {
    const officeToDelete = deleteModal.office;
    if (!officeToDelete) return;

    setDeleteModal((prev) => ({ ...prev, isDeleting: true }));

    const res = await officeService.deleteOffice(officeToDelete.id);

    if (res.success) {
      showToast('success', `Room ${officeToDelete.officeNumber} was deleted successfully.`);
      setDeleteModal({ isOpen: false, office: null, isDeleting: false });
      // Reload actual database records
      fetchOffices();
    } else {
      showToast('error', res.error || 'Failed to delete office.');
      setDeleteModal((prev) => ({ ...prev, isDeleting: false }));
    }
  };

  // Handle Update button click in Action column
  const handleUpdateClick = (office) => {
    setUpdateModal({
      isOpen: true,
      office,
    });
  };

  // Handle Add Office - saves directly to backend database
  const handleSaveOffice = async (newOfficeData) => {
    setAddModal((prev) => ({ ...prev, isSaving: true }));

    const res = await officeService.saveOffice(newOfficeData);

    if (res.success) {
      showToast('success', `Room ${newOfficeData.officeNumber} registered successfully.`);
      setAddModal({ isOpen: false, isSaving: false });
      // Reload actual database records
      fetchOffices();
    } else {
      showToast('error', res.error || 'Failed to save office.');
      setAddModal((prev) => ({ ...prev, isSaving: false }));
    }
  };

  return (
    <div className="app-layout">
      {/* Top Navigation */}
      <Navbar
        onOpenAddModal={() => setAddModal({ isOpen: true, isSaving: false })}
      />

      <main className="main-content">
        <div className="page-header">
          <div>
            <h1 className="page-title">Available Offices</h1>
            <p className="page-subtitle">
              Manage clinical consultation offices and doctor room assignments.
            </p>
          </div>
        </div>

        {/* Stats Overview */}
        <StatsCards
          totalOffices={totalCount}
          availableOffices={availableCount}
          occupiedOffices={occupiedCount}
        />

        {/* Search Bar */}
        <SearchBar
          searchTerm={searchTerm}
          setSearchTerm={setSearchTerm}
          activeFilter={activeFilter}
          setActiveFilter={setActiveFilter}
          totalResults={filteredOffices.length}
        />

        {/* Real Database Offices Table / Loading */}
        {loading ? (
          <div className="loading-container">
            <Loader2 size={32} className="spin text-primary" />
            <span className="loading-text">Loading offices...</span>
          </div>
        ) : (
          <OfficeTable
            offices={filteredOffices}
            onDeleteClick={handleDeleteClick}
            onUpdateClick={handleUpdateClick}
            searchTerm={searchTerm}
            onResetSearch={() => {
              setSearchTerm('');
              setActiveFilter('all');
            }}
          />
        )}
      </main>

      {/* Delete Confirmation Modal */}
      <DeleteModal
        office={deleteModal.office}
        isOpen={deleteModal.isOpen}
        onClose={() => setDeleteModal({ isOpen: false, office: null, isDeleting: false })}
        onConfirm={handleConfirmDelete}
        isDeleting={deleteModal.isDeleting}
      />

      {/* Edit Office Modal */}
      <UpdateModal
        office={updateModal.office}
        isOpen={updateModal.isOpen}
        onClose={() => setUpdateModal({ isOpen: false, office: null })}
      />

      {/* Add Office Modal */}
      <AddOfficeModal
        isOpen={addModal.isOpen}
        onClose={() => setAddModal({ isOpen: false, isSaving: false })}
        onSave={handleSaveOffice}
        isSaving={addModal.isSaving}
      />

      {/* Toast Feedback */}
      <Toast toast={toast} onClose={() => setToast(null)} />
    </div>
  );
}
