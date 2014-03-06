package passer.factory;

import passer.connection.UserDAO;
import passer.connection.UserLocDAO;
import passer.connection.impl.UserDAOImpl;
import passer.connection.impl.UserLocDAOImpl;

public class DAOFactory {
	public static UserDAO getUserDAO(){
		return new UserDAOImpl();
	}
	
	public static UserLocDAO getUserLocDAO(){
		return new UserLocDAOImpl();
	}
//	public static AuthorityDAO getAuthorityDAO(){
//		return new AuthorityDAOImpl();
//	}
//	public static StockDAO getStockDAO(){
//		return new StockDAOImpl();
//	}
//	public static StockMarketDataDAO getStockMarketDAO(){
//		return new StockMarketDataDAOImpl();
//	}
//	public static SectorDAO getSectorDAO(){
//		return new SectorDAOImpl();
//	}
//	public static ThemeDAO getThemeDAO(){
//		return new ThemeDAOImpl();
//	}
//	public static RatioDAO getRatioDAO(){
//		return new RatioDAOImpl();
//	}
//	public static TemplateDAO getTemplateDAO(){
//		return new TemplateDAOImpl();
//	}
//	public static PortfolioDAO getPortfolioDAO(){
//		return new PortfolioDAOImpl();
//	}
//	public static PortfolioStockDAO getPortfolioStockDAO(){
//		return new PortfolioStockDAOImpl();
//	}
//	public static NewsDAO getNewsDAO(){
//		return new NewsDAOImpl();
//	}
//	public static ObservationDAO getObservationDAO(){
//		return new ObservationDAOImpl();
//	}
//	public static IndexDAO getIndexDAO(){
//		return new IndexDAOImpl();
//	}
//	public static StockIndexDAO getStockIndexDAO(){
//		return new StockIndexDAOImpl();
//	}
//	public static VisitDAO getVisitDAO(){
//		return new VisitDAOImpl();
//	}
//	public static BondDAO getBondDAO(){
//		return new BondDAOImpl();
//	}
//	public static StockFinancialDataDAO getStockFinancialDataDAO(){
//		return new StockFinancialDataDAOImpl();
//	}
//	public static AdDAO getAdDAO(){
//		return new AdDAOImpl();
//	}
//	public static PortfolioBondDAO getPortfolioBondDAO(){
//		return new PortfolioBondDAOImpl();
//	}
//	public static BondMarketDataDAO getBondMarketDataDAO(){
//		return new BondMarketDataDaoImpl();
//	}
//	public static BondFinancialDataDAO getBondFinancialDataDAO(){
//		return new BondFinancialDataDaoImpl();
//	}
//	public static FutureDAO getFutureDAO(){
//		return new FutureDAOImpl();
//	}
//	public static PortfolioFutureDAO getPortfolioFutureDAO(){
//		return new PortfolioFutureDAOImpl();
//	}
//	public static FundDAO getFundDAO(){
//		return new FundDAOImpl();
//	}
//	public static PortfolioFundDAO getPortfolioFundDAO(){
//		return new PortfolioFundDAOImpl();
//	}
//	public static TrustDAO getTrustDAO(){
//		return new TrustDAOImpl();
//	}
//	public static PortfolioTrustDAO getPortfolioTrustDAO(){
//		return new PortfolioTrustDAOImpl();
//	}
//	public static FundMarketDataDAO getFundMarketDataDAO(){
//		return new FundMarketDataDaoImpl();
//	}
//	public static FundFinancialDataDAO getFundFinancialDataDAO(){
//		return new FundFinancialDataDaoImpl();
//	}
//	public static FutureMarketDataDAO getFutureMarketDataDAO(){
//		return new FutureMarketDataDaoImpl();
//	}
//	public static TrustMarketDataDAO getTrustMarketDataDAO(){
//		return new TrustMarketDataDaoImpl();
//	}
//	public static TrustFinancialDataDAO getTrustFinancialDataDAO(){
//		return new TrustFinancialDataDaoImpl();
//	}
//	public static PerfinDAO getPerfinDAO(){
//		return new PerfinDAOImpl();
//	}
//	public static PerfinMarketDataDAO getPerfinMarketDataDAO(){
//		return new PerfinMarketDataDAOImpl();
//	}
//	public static PerfinFinancialDataDAO getPerfinFinancialDataDAO(){
//		return new PerfinFinancialDataDAOImpl();
//	}
//	public static PortfolioPerfinDAO getPortfolioPerfinDAO(){
//		return new PortfolioPerfinDAOImpl();
//	}
//	public static CommentDAO getCommentDAO(){
//		return new CommentDAOImpl();
//	}
//	public static TopicDAO getTopicDAO(){
//		return new TopicDAOImpl();
//	}
//	public static ForumDAOImpl getForumDAO(){
//		return new ForumDAOImpl();
//	}
}
