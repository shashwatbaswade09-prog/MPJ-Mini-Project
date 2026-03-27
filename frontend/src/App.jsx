import React, { useState } from 'react';
import Login from './components/Login';
import Dashboard from './components/Dashboard';

function App() {
  const [account, setAccount] = useState(null);

  const handleLogin = (acc) => {
    setAccount(acc);
  };

  const handleLogout = () => {
    setAccount(null);
  };

  return (
    <div className="min-h-screen bg-slate-50">
      <header className="bg-white shadow">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4 flex justify-between items-center">
          <h1 className="text-xl font-bold text-slate-800">MPJ Vault</h1>
          {account && (
            <button
              onClick={handleLogout}
              className="text-sm font-medium text-slate-600 hover:text-slate-900"
            >
              Sign Out
            </button>
          )}
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {!account ? (
          <Login onLogin={handleLogin} />
        ) : (
          <Dashboard account={account} refreshAccount={handleLogin} />
        )}
      </main>
    </div>
  );
}

export default App;
