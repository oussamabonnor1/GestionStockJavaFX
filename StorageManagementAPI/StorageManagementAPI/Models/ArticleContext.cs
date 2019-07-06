
using Microsoft.EntityFrameworkCore;

namespace StorageManagementAPI.Models
{
    public class ArticleContext : DbContext
    {
        public DbSet<Article> articles { get; set; }

        public ArticleContext(DbContextOptions<ArticleContext> options) : base (options)
        {

        }
    }
}
