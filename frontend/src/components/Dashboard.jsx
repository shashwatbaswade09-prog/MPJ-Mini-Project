import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { ArrowUpCircle, ArrowDownCircle, RefreshCw, Activity, DollarSign } from 'lucide-react';

const API_URL = 'http://localhost:8080/api';

const Dashboard = ({ account, refreshAccount }) => {
  const [transactions, setTransactions] = useState([]);
  const [actionType, setActionType] = useState(null); // 'deposit', 'withdraw', 'transfer'
  const [amount, setAmount] = useState('');
  const [toAccount, setToAccount] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const fetchHistory = async () => {
    try {
      const res = await axios.get(`${API_URL}/accounts/${account.accountId}/history`);
      setTransactions(res.data.reverse()); // newest first
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchHistory();
    // eslint-disable-next-line
  }, [account.accountId]);

  const handleAction = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');
    try {
      let endpoint = '';
      let payload = { accountId: account.accountId, amount: parseFloat(amount) };

      if (actionType === 'deposit') endpoint = '/transactions/deposit';
      if (actionType === 'withdraw') endpoint = '/transactions/withdraw';
      if (actionType === 'transfer') {
        endpoint = '/transactions/transfer';
        payload = { fromAccount: account.accountId, toAccount: parseInt(toAccount), amount: parseFloat(amount) };
      }

      const res = await axios.post(`${API_URL}${endpoint}`, payload);
      setMessage(res.data.message);
      
      // Refresh User data & History
      const updatedAcc = await axios.get(`${API_URL}/accounts/${account.accountId}`);
      refreshAccount(updatedAcc.data);
      fetchHistory();
      
      // Reset form
      setAmount('');
      setToAccount('');
      setTimeout(() => { setMessage(''); setActionType(null); }, 3000);
      
    } catch (err) {
      setError(err.response?.data?.error || 'Transaction failed');
    }
  };

  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {/* Balance Card */}
        <div className="col-span-1 md:col-span-2 bg-slate-900 rounded-2xl p-8 text-white shadow-xl relative overflow-hidden">
          <div className="absolute top-0 right-0 -mr-8 -mt-8 w-48 h-48 bg-slate-800 rounded-full opacity-50"></div>
          <div className="relative z-10">
            <h3 className="text-slate-400 font-medium mb-1">Available Balance</h3>
            <div className="text-5xl font-bold tracking-tight">${account.balance.toFixed(2)}</div>
            <div className="mt-8 flex items-center justify-between text-sm">
              <span className="text-slate-400">Account #{account.accountId}</span>
              <span className="text-slate-400">Customer #{account.customerId}</span>
            </div>
          </div>
        </div>

        {/* Quick Actions */}
        <div className="bg-white rounded-2xl p-6 shadow-sm border border-slate-100 flex flex-col justify-between">
          <h3 className="font-semibold text-slate-800 mb-4">Quick Actions</h3>
          <div className="space-y-3">
            <button
              onClick={() => { setActionType('deposit'); setError(''); setMessage(''); }}
              className="w-full flex items-center justify-between p-3 rounded-xl bg-blue-50 text-blue-700 hover:bg-blue-100 transition-colors font-medium"
            >
              <span className="flex items-center gap-2"><ArrowUpCircle className="w-5 h-5"/> Deposit</span>
            </button>
            <button
              onClick={() => { setActionType('withdraw'); setError(''); setMessage(''); }}
              className="w-full flex items-center justify-between p-3 rounded-xl bg-slate-50 text-slate-700 hover:bg-slate-100 transition-colors font-medium border border-slate-200"
            >
              <span className="flex items-center gap-2"><ArrowDownCircle className="w-5 h-5"/> Withdraw</span>
            </button>
            <button
              onClick={() => { setActionType('transfer'); setError(''); setMessage(''); }}
              className="w-full flex items-center justify-between p-3 rounded-xl bg-slate-900 text-white hover:bg-slate-800 transition-colors font-medium"
            >
              <span className="flex items-center gap-2"><RefreshCw className="w-5 h-5"/> Transfer</span>
            </button>
          </div>
        </div>
      </div>

      {actionType && (
        <div className="bg-white rounded-2xl p-6 shadow-sm border border-slate-100 mb-6 transition-all">
          <h3 className="text-lg font-semibold capitalize flex items-center gap-2 mb-4 text-slate-800">
            {actionType === 'deposit' && <ArrowUpCircle className="text-blue-500"/>}
            {actionType === 'withdraw' && <ArrowDownCircle className="text-orange-500"/>}
            {actionType === 'transfer' && <RefreshCw className="text-purple-500"/>}
            {actionType} Funds
          </h3>

          {message && <div className="mb-4 p-3 bg-green-50 text-green-700 rounded-lg text-sm border border-green-100">{message}</div>}
          {error && <div className="mb-4 p-3 bg-red-50 text-red-600 rounded-lg text-sm border border-red-100">{error}</div>}

          <form onSubmit={handleAction} className="flex flex-col md:flex-row gap-4 items-end">
            <div className="flex-1 w-full">
              <label className="block text-sm font-medium text-slate-700 mb-1">Amount ($)</label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <DollarSign className="h-5 w-5 text-slate-400" />
                </div>
                <input
                  type="number"
                  step="0.01"
                  required
                  min="0.01"
                  className="pl-10 w-full px-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
                  value={amount}
                  onChange={(e) => setAmount(e.target.value)}
                />
              </div>
            </div>

            {actionType === 'transfer' && (
              <div className="flex-1 w-full">
                <label className="block text-sm font-medium text-slate-700 mb-1">To Account ID</label>
                <input
                  type="number"
                  required
                  className="w-full px-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
                  value={toAccount}
                  onChange={(e) => setToAccount(e.target.value)}
                />
              </div>
            )}

            <button
              type="submit"
              className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg font-medium transition-colors h-10 w-full md:w-auto"
            >
              Confirm
            </button>
            <button
              type="button"
              onClick={() => setActionType(null)}
              className="bg-slate-100 hover:bg-slate-200 text-slate-700 px-6 py-2 rounded-lg font-medium transition-colors h-10 w-full md:w-auto"
            >
              Cancel
            </button>
          </form>
        </div>
      )}

      {/* History */}
      <div className="bg-white rounded-2xl shadow-sm border border-slate-100 overflow-hidden">
        <div className="px-6 py-5 border-b border-slate-100 flex items-center justify-between">
          <h3 className="font-semibold text-slate-800 flex items-center gap-2">
            <Activity className="w-5 h-5 text-slate-500"/> Recent Transactions
          </h3>
        </div>
        <div className="divide-y divide-slate-100">
          {transactions.length === 0 ? (
            <div className="px-6 py-8 text-center text-slate-500 text-sm">No transactions yet.</div>
          ) : (
            transactions.map((tx) => (
              <div key={tx.id} className="px-6 py-4 flex items-center justify-between hover:bg-slate-50 transition-colors">
                <div className="flex items-center gap-4">
                  <div className={`w-10 h-10 rounded-full flex items-center justify-center ${
                    tx.type.includes('Deposit') || tx.type.includes('Credit') 
                      ? 'bg-green-100 text-green-600' 
                      : 'bg-orange-100 text-orange-600'
                  }`}>
                    {tx.type.includes('Deposit') || tx.type.includes('Credit') ? <ArrowUpCircle className="w-5 h-5"/> : <ArrowDownCircle className="w-5 h-5"/>}
                  </div>
                  <div>
                    <div className="font-medium text-slate-800">{tx.type}</div>
                    <div className="text-xs text-slate-500">
                      {new Date(tx.date).toLocaleDateString()} at {new Date(tx.date).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                    </div>
                  </div>
                </div>
                <div className={`font-semibold ${
                  tx.type.includes('Deposit') || tx.type.includes('Credit') ? 'text-green-600' : 'text-slate-800'
                }`}>
                  {tx.type.includes('Deposit') || tx.type.includes('Credit') ? '+' : '-'}${tx.amount.toFixed(2)}
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
