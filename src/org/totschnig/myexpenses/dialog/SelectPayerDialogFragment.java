/*   This file is part of My Expenses.
 *   My Expenses is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   My Expenses is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with My Expenses.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.totschnig.myexpenses.dialog;

import org.totschnig.myexpenses.R;
import org.totschnig.myexpenses.provider.DatabaseConstants;
import org.totschnig.myexpenses.provider.TransactionProvider;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.LoaderManager;

public class SelectPayerDialogFragment extends SelectFromMappedTableDialogFragment implements OnClickListener,
    LoaderManager.LoaderCallbacks<Cursor>
{

  @Override
  int getDialogTitle() {
    return R.string.search_payee;
  }
  @Override
  int getCriteriaTitle() {
    return R.string.payer_or_payee;
  }
  @Override
  int getCommand() {
    return R.id.FILTER_PAYER_COMMAND;
  }
  @Override
  String getColumn() {
    return DatabaseConstants.KEY_PAYEEID;
  }
  @Override
  Uri getUri() {
    return TransactionProvider.MAPPED_PAYEES_URI;
  }
  /**
   * @param account_id
   * @return
   */
  public static final SelectPayerDialogFragment newInstance(long account_id) {
    SelectPayerDialogFragment dialogFragment = new SelectPayerDialogFragment();
    Bundle args = new Bundle();
    args.putLong(DatabaseConstants.KEY_ACCOUNTID, account_id);
    dialogFragment.setArguments(args);
    return dialogFragment;
  }
}