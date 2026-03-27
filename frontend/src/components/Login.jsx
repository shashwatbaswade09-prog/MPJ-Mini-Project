import React, { useState } from 'react';
import axios from 'axios';
import { CreditCard, ArrowRight, UserPlus } from 'lucide-react';

const API_URL = 'http://localhost:8080/api';

const Login = ({ onLogin }) => {
  const [accountId, setAccountId] = useState('');
  const [error, setError] = useState('');
  const [isRegistering, setIsRegistering] = useState(false);
  const [registerData, setRegisterData] = useState({ accountId: '', customerId: '', balance: '' });

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const response = await axios.get(`${API_URL}/accounts/${accountId}`);
      onLogin(response.data);
    } catch (err) {
      setError('Account not found. Please try again or create an account.');
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await axios.post(`${API_URL}/accounts`, registerData);
      setAccountId(registerData.accountId);
      setIsRegistering(false);
      
      const response = await axios.get(`${API_URL}/accounts/${registerData.accountId}`);
      onLogin(response.data);
    } catch (err) {
      setError(err.response?.data?.error || 'Registration failed');
    }
  };

  return (
    <div className="max-w-md mx-auto mt-16 bg-white p-8 rounded-2xl shadow-xl shadow-slate-200 border border-slate-100">
      <div className="text-center mb-8">
        <div className="mx-auto w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center mb-4">
          <CreditCard className="w-6 h-6 text-blue-600" />
        </div>
        <h2 className="text-2xl font-bold text-slate-800">
          {isRegistering ? 'Create Account' : 'Welcome Back'}
        </h2>
        <p className="text-slate-500 mt-2">
          {isRegistering ? 'Sign up to start saving' : 'Enter your Account ID to continue'}
        </p>
      </div>

      {error && (
        <div className="mb-4 p-3 bg-red-50 text-red-600 rounded-lg text-sm border border-red-100">
          {error}
        </div>
      )}

      {isRegistering ? (
        <form onSubmit={handleRegister} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Account ID</label>
            <input
              type="number"
              required
              className="w-full px-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
              value={registerData.accountId}
              onChange={(e) => setRegisterData({ ...registerData, accountId: e.target.value })}
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Customer ID</label>
            <input
              type="number"
              required
              className="w-full px-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
              value={registerData.customerId}
              onChange={(e) => setRegisterData({ ...registerData, customerId: e.target.value })}
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Initial Balance ($)</label>
            <input
              type="number"
              required
              className="w-full px-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
              value={registerData.balance}
              onChange={(e) => setRegisterData({ ...registerData, balance: e.target.value })}
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-2.5 rounded-lg transition-colors flex items-center justify-center gap-2"
          >
            Create Account <UserPlus className="w-4 h-4" />
          </button>
        </form>
      ) : (
        <form onSubmit={handleLogin} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Account ID</label>
            <input
              type="number"
              required
              placeholder="e.g. 1001"
              className="w-full px-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
              value={accountId}
              onChange={(e) => setAccountId(e.target.value)}
            />
          </div>
          <button
            type="submit"
            className="w-full bg-slate-900 hover:bg-slate-800 text-white font-medium py-2.5 rounded-lg transition-colors flex items-center justify-center gap-2"
          >
            Access Dashboard <ArrowRight className="w-4 h-4" />
          </button>
        </form>
      )}

      <div className="mt-6 text-center">
        <button
          onClick={() => { setIsRegistering(!isRegistering); setError(''); }}
          className="text-sm font-medium text-blue-600 hover:text-blue-700"
        >
          {isRegistering ? 'Already have an account? Login' : 'Need an account? Register'}
        </button>
      </div>
    </div>
  );
};

export default Login;
